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