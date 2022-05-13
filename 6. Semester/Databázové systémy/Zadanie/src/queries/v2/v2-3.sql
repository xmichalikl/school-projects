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
