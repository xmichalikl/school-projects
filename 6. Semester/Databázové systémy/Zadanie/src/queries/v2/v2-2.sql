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