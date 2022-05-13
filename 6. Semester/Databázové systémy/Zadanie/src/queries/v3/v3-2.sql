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