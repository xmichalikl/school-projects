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