### [Server] link: 
https://fiit-dbs-xmichalikl.herokuapp.com/

[Server]: <https://fiit-dbs-xmichalikl.herokuapp.com>

### V2 SQL [Queries](/queries/v2)
#### /v2/patches/
``` sql
-- MISSING
```

#### /v2/players/{player_id}/game_exp/
``` sql
SELECT 
pl.id,
COALESCE (pl.nick, 'unknown') AS player_nick, 
mpd.match_id,
hr.localized_name AS hero_localized_name,
CAST (ROUND (mc.duration/60.0, 2) AS FLOAT) AS match_duration_minutes,
COALESCE (mpd.xp_hero, 0) +
COALESCE (mpd.xp_creep,	0) +
COALESCE (mpd.xp_other,	0) +
COALESCE (mpd.xp_roshan, 0) AS experiences_gained,
MAX(mpd.level) AS level_gained,
CASE
    WHEN mpd.player_slot >= 0 AND mpd.player_slot <= 4 THEN mc.radiant_win
    WHEN mpd.player_slot >= 128 AND mpd.player_slot <= 132 THEN NOT mc.radiant_win
    ELSE NULL
END AS winner
FROM players AS pl
JOIN matches_players_details AS mpd ON mpd.player_id = pl.id
JOIN heroes AS hr ON hr.id = mpd.hero_id
JOIN matches AS mc ON mc.id = mpd.match_id
WHERE pl.id = :player_id
GROUP BY (pl.id, mpd.match_id, hero_localized_name, match_duration_minutes, experiences_gained, winner)
ORDER BY mpd.match_id;
```

#### /v2/players/{player_id}/game_objectives/
``` sql
SELECT
pl.id, 
COALESCE (pl.nick, 'unknown') AS player_nick, 
mpd.match_id,
hr.localized_name AS hero_localized_name,
COALESCE (gmo.subtype, 'NO_ACTION') AS hero_action, 
CAST (COUNT(*) AS INTEGER) AS count
FROM players AS pl
JOIN matches_players_details AS mpd ON mpd.player_id = pl.id
JOIN heroes AS hr ON hr.id = mpd.hero_id
JOIN matches AS mc ON mc.id = mpd.match_id
LEFT JOIN game_objectives AS gmo ON gmo.match_player_detail_id_1 = mpd.id
WHERE pl.id = :player_id
GROUP BY (pl.id, mpd.match_id, hero_localized_name, hero_action)
ORDER BY mpd.match_id;
```

#### /v2/players/{player_id}/abilities/
``` sql
SELECT
pl.id, 
COALESCE (pl.nick, 'unknown') AS player_nick, 
mpd.match_id,
hr.localized_name AS hero_localized_name,
COALESCE (gmo.subtype, 'NO_ACTION') AS hero_action, 
CAST (COUNT(*) AS INTEGER) AS count
FROM players AS pl
JOIN matches_players_details AS mpd ON mpd.player_id = pl.id
JOIN heroes AS hr ON hr.id = mpd.hero_id
JOIN matches AS mc ON mc.id = mpd.match_id
LEFT JOIN game_objectives AS gmo ON gmo.match_player_detail_id_1 = mpd.id
WHERE pl.id = :player_id
GROUP BY (pl.id, mpd.match_id, hero_localized_name, hero_action)
ORDER BY mpd.match_id;
```


### V3 SQL [Queries](/queries/v3)

#### /v3/matches/{match_id}/top_purchases/
``` sql
SELECT * FROM (
	SELECT 
	mc.id AS match_id, 
	hr.id AS hero_id, 
	hr.localized_name AS hero_name, 
	itm.id AS item_id, 
	itm.name AS item_name,
	CAST (COUNT(*) AS INTEGER) AS item_purchases,
	ROW_NUMBER() OVER (
		PARTITION BY hr.id ORDER BY COUNT(*) DESC, itm.name
	) AS row_num 

	FROM matches AS mc
	JOIN matches_players_details AS mpd ON mpd.match_id = mc.id
	JOIN heroes AS hr ON hr.id = mpd.hero_id
	JOIN purchase_logs AS plog ON plog.match_player_detail_id = mpd.id
	JOIN items AS itm ON itm.id = plog.item_id
	WHERE mc.id = :match_id
	AND CASE 
		WHEN mc.radiant_win THEN mpd.player_slot BETWEEN 0 AND 4
		WHEN NOT mc.radiant_win THEN mpd.player_slot BETWEEN 128 AND 132
	END
	GROUP BY (mc.id, mpd.player_slot, hr.id, plog.item_id, itm.id)
	ORDER BY hr.id, item_purchases DESC
) top_purchases	
WHERE row_num <= 5;
```

#### /v3/abilities/{ability_id}/usage/
``` sql
SELECT ability_id, ability_name, hero_id, hero_name, winner, bucket, "count" 
FROM (
	SELECT ability_id, ability_name, hero_id, hero_name, winner, bucket, "count",
	ROW_NUMBER() OVER (
		PARTITION BY hero_id, hero_name, winner ORDER BY "count" DESC
	) AS win_partition
	FROM (
		SELECT 
		ab.id AS ability_id,
		ab.name AS ability_name,
		hr.id AS hero_id, 
		hr.localized_name AS hero_name, 
		
		CASE 
			WHEN mpd.player_slot BETWEEN 0 AND 4 THEN mc.radiant_win
			WHEN mpd.player_slot BETWEEN 128 AND 132 THEN NOT mc.radiant_win
		END AS winner,

		CASE
			WHEN FLOOR(100 * abu.time / mc.duration) BETWEEN  0 AND  9 THEN '0-9'
			WHEN FLOOR(100 * abu.time / mc.duration) BETWEEN 10 AND 19 THEN '10-19'
			WHEN FLOOR(100 * abu.time / mc.duration) BETWEEN 20 AND 29 THEN '20-29'
			WHEN FLOOR(100 * abu.time / mc.duration) BETWEEN 30 AND 39 THEN '30-39'
			WHEN FLOOR(100 * abu.time / mc.duration) BETWEEN 40 AND 49 THEN '40-49'
			WHEN FLOOR(100 * abu.time / mc.duration) BETWEEN 50 AND 59 THEN '50-59'
			WHEN FLOOR(100 * abu.time / mc.duration) BETWEEN 60 AND 69 THEN '60-69'
			WHEN FLOOR(100 * abu.time / mc.duration) BETWEEN 70 AND 79 THEN '70-79'
			WHEN FLOOR(100 * abu.time / mc.duration) BETWEEN 80 AND 89 THEN '80-89'
			WHEN FLOOR(100 * abu.time / mc.duration) BETWEEN 90 AND 99 THEN '90-99'
			ELSE '100-109'
		END AS bucket,
		CAST (COUNT(*) AS INTEGER) AS "count"

		FROM matches AS mc
		JOIN matches_players_details AS mpd ON mpd.match_id = mc.id
		JOIN heroes AS hr ON hr.id = mpd.hero_id
		JOIN ability_upgrades AS abu ON abu.match_player_detail_id = mpd.id
		JOIN abilities AS ab ON ab.id = abu.ability_id
		WHERE ab.id = :ability_id
		GROUP BY (ab.id, ab.name, hr.id, hr.name, winner, bucket)
	) records
	ORDER BY hero_id, hero_name, winner, win_partition
) top_abilities
WHERE win_partition = 1
```

#### /v3/statistics/tower_kills/
``` sql
SELECT hero_id, hero_name, "tower_kills"
FROM (
	SELECT match_id, hero_id, hero_name, seq_num, "tower_kills",
	
	ROW_NUMBER() OVER (
		PARTITION BY hero_id ORDER BY "tower_kills" DESC
	) AS tower_kills_partition

	FROM (
		SELECT match_id, hero_id, hero_name, seq_num, CAST (COUNT (seq_num) AS INTEGER) AS "tower_kills"
		FROM (
			SELECT match_id, hero_id, hero_name, prev_match_id, prev_hero_id, "rank",
			
			CASE 
				WHEN hero_id = prev_hero_id AND match_id = prev_match_id THEN 1 ELSE 0 
			END AS seq_flag,

			SUM ( CASE 
				WHEN hero_id = prev_hero_id THEN 0 ELSE 1 
			END ) OVER (ORDER BY "rank") AS seq_num

			FROM (
				SELECT
				mc.id AS match_id, 
				hr.id AS hero_id, 
				hr.localized_name AS hero_name, 
				
				LAG(match_id) OVER(ORDER BY match_id) AS prev_match_id,
				LAG(hero_id) OVER(ORDER BY match_id) AS prev_hero_id,
				
				ROW_NUMBER() OVER (
					ORDER BY mc.id, gob.time
				) AS "rank"

				FROM matches AS mc
				JOIN matches_players_details AS mpd ON mpd.match_id = mc.id
				JOIN heroes AS hr ON hr.id = mpd.hero_id
				JOIN game_objectives AS gob ON gob.match_player_detail_id_1 = mpd.id
				WHERE gob.subtype = 'CHAT_MESSAGE_TOWER_KILL'
			) "ranks"
		) "all_sequences"
		GROUP BY (match_id, hero_id, hero_name, seq_num)
	) "sequences_count"
) "top_tower_kills"
WHERE tower_kills_partition = 1
ORDER BY "tower_kills" DESC, hero_name
```

### V4 [Prisma] ORM [Models](/prisma/schema.prisma) & [Migration](/prisma/migrations/20220508210656_initial_migration/migration.sql)

[Prisma]: <https://www.prisma.io>

#### Rewriten endpoints:
- ##### /v4/players/{player_id}/game_exp/
- ##### /v4/players/{player_id}/game_objectives/