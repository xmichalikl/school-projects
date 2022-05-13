SELECT
pl.id, 
COALESCE (pl.nick, 'unknown') AS player_nick, 
mpd.match_id,
hr.localized_name AS hero_localized_name,
ab.name AS ability_name,
CAST (COUNT(*) AS INTEGER) AS count,
MAX(abu.level) AS upgrade_level 

FROM players AS pl
JOIN matches_players_details AS mpd ON mpd.player_id = pl.id
JOIN heroes AS hr ON hr.id = mpd.hero_id
JOIN matches AS mc ON mc.id = mpd.match_id
JOIN ability_upgrades AS abu ON abu.match_player_detail_id = mpd.id
JOIN abilities AS ab ON ab.id = abu.ability_id
WHERE pl.id = :player_id
GROUP BY (pl.id, mpd.match_id, hero_localized_name, ab.name)
ORDER BY mpd.match_id;