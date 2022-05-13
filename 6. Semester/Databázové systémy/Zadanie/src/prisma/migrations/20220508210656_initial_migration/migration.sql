-- CreateTable
CREATE TABLE "abilities" (
    "id" INTEGER NOT NULL,
    "name" TEXT,

    CONSTRAINT "abilities_pk" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "ability_upgrades" (
    "id" INTEGER NOT NULL DEFAULT nextval('ability_upgrades_id_seq'::regclass),
    "ability_id" INTEGER,
    "match_player_detail_id" INTEGER,
    "level" INTEGER,
    "time" INTEGER,

    CONSTRAINT "ability_upgrades_pk" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "chats" (
    "id" INTEGER NOT NULL DEFAULT nextval('chats_id_seq'::regclass),
    "match_player_detail_id" INTEGER,
    "message" TEXT,
    "time" INTEGER,
    "nick" TEXT,

    CONSTRAINT "chats_pk" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "cluster_regions" (
    "id" INTEGER NOT NULL,
    "name" TEXT,

    CONSTRAINT "cluster_regions_pk" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "game_objectives" (
    "id" INTEGER NOT NULL DEFAULT nextval('game_objectives_id_seq'::regclass),
    "match_player_detail_id_1" INTEGER,
    "match_player_detail_id_2" INTEGER,
    "key" INTEGER,
    "subtype" TEXT,
    "team" INTEGER,
    "time" INTEGER,
    "value" INTEGER,
    "slot" INTEGER,

    CONSTRAINT "game_objectives_pk" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "heroes" (
    "id" INTEGER NOT NULL,
    "name" TEXT,
    "localized_name" TEXT,

    CONSTRAINT "heroes_pk" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "items" (
    "id" INTEGER NOT NULL,
    "name" TEXT,

    CONSTRAINT "items_pk" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "matches" (
    "id" INTEGER NOT NULL,
    "cluster_region_id" INTEGER,
    "start_time" INTEGER,
    "duration" INTEGER,
    "tower_status_radiant" INTEGER,
    "tower_status_dire" INTEGER,
    "barracks_status_radiant" INTEGER,
    "barracks_status_dire" INTEGER,
    "first_blood_time" INTEGER,
    "game_mode" INTEGER,
    "radiant_win" BOOLEAN,
    "negative_votes" INTEGER,
    "positive_votes" INTEGER,

    CONSTRAINT "matches_pk" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "matches_players_details" (
    "id" INTEGER NOT NULL DEFAULT nextval('matches_players_details_id_seq'::regclass),
    "match_id" INTEGER,
    "player_id" INTEGER,
    "hero_id" INTEGER,
    "player_slot" INTEGER,
    "gold" INTEGER,
    "gold_spent" INTEGER,
    "gold_per_min" INTEGER,
    "xp_per_min" INTEGER,
    "kills" INTEGER,
    "deaths" INTEGER,
    "assists" INTEGER,
    "denies" INTEGER,
    "last_hits" INTEGER,
    "stuns" INTEGER,
    "hero_damage" INTEGER,
    "hero_healing" INTEGER,
    "tower_damage" INTEGER,
    "item_id_1" INTEGER,
    "item_id_2" INTEGER,
    "item_id_3" INTEGER,
    "item_id_4" INTEGER,
    "item_id_5" INTEGER,
    "item_id_6" INTEGER,
    "level" INTEGER,
    "leaver_status" INTEGER,
    "xp_hero" INTEGER,
    "xp_creep" INTEGER,
    "xp_roshan" INTEGER,
    "xp_other" INTEGER,
    "gold_other" INTEGER,
    "gold_death" INTEGER,
    "gold_buyback" INTEGER,
    "gold_abandon" INTEGER,
    "gold_sell" INTEGER,
    "gold_destroying_structure" INTEGER,
    "gold_killing_heroes" INTEGER,
    "gold_killing_creeps" INTEGER,
    "gold_killing_roshan" INTEGER,
    "gold_killing_couriers" INTEGER,

    CONSTRAINT "matches_players_details_pk" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "patches" (
    "id" INTEGER NOT NULL DEFAULT nextval('patches_id_seq'::regclass),
    "name" TEXT NOT NULL,
    "release_date" TIMESTAMP(6) NOT NULL,

    CONSTRAINT "patches_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "player_actions" (
    "id" INTEGER NOT NULL DEFAULT nextval('player_actions_id_seq'::regclass),
    "unit_order_none" INTEGER,
    "unit_order_move_to_position" INTEGER,
    "unit_order_move_to_target" INTEGER,
    "unit_order_attack_move" INTEGER,
    "unit_order_attack_target" INTEGER,
    "unit_order_cast_position" INTEGER,
    "unit_order_cast_target" INTEGER,
    "unit_order_cast_target_tree" INTEGER,
    "unit_order_cast_no_target" INTEGER,
    "unit_order_cast_toggle" INTEGER,
    "unit_order_hold_position" INTEGER,
    "unit_order_train_ability" INTEGER,
    "unit_order_drop_item" INTEGER,
    "unit_order_give_item" INTEGER,
    "unit_order_pickup_item" INTEGER,
    "unit_order_pickup_rune" INTEGER,
    "unit_order_purchase_item" INTEGER,
    "unit_order_sell_item" INTEGER,
    "unit_order_disassemble_item" INTEGER,
    "unit_order_move_item" INTEGER,
    "unit_order_cast_toggle_auto" INTEGER,
    "unit_order_stop" INTEGER,
    "unit_order_buyback" INTEGER,
    "unit_order_glyph" INTEGER,
    "unit_order_eject_item_from_stash" INTEGER,
    "unit_order_cast_rune" INTEGER,
    "unit_order_ping_ability" INTEGER,
    "unit_order_move_to_direction" INTEGER,
    "match_player_detail_id" INTEGER,

    CONSTRAINT "player_actions_pk" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "player_ratings" (
    "id" INTEGER NOT NULL DEFAULT nextval('player_ratings_id_seq'::regclass),
    "player_id" INTEGER,
    "total_wins" INTEGER,
    "total_matches" INTEGER,
    "trueskill_mu" DECIMAL,
    "trueskill_sigma" DECIMAL,

    CONSTRAINT "player_ratings_pk" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "player_times" (
    "id" INTEGER NOT NULL DEFAULT nextval('player_times_id_seq'::regclass),
    "match_player_detail_id" INTEGER,
    "time" INTEGER,
    "gold" INTEGER,
    "lh" INTEGER,
    "xp" INTEGER,

    CONSTRAINT "player_times_pk" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "players" (
    "id" INTEGER NOT NULL,
    "name" TEXT,
    "nick" TEXT,

    CONSTRAINT "players_pk" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "purchase_logs" (
    "id" INTEGER NOT NULL DEFAULT nextval('purchase_logs_id_seq'::regclass),
    "match_player_detail_id" INTEGER,
    "item_id" INTEGER,
    "time" INTEGER,

    CONSTRAINT "purchase_logs_pk" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "teamfights" (
    "id" INTEGER NOT NULL DEFAULT nextval('teamfights_id_seq'::regclass),
    "match_id" INTEGER,
    "start_teamfight" INTEGER,
    "end_teamfight" INTEGER,
    "last_death" INTEGER,
    "deaths" INTEGER,

    CONSTRAINT "teamfights_pk" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "teamfights_players" (
    "id" INTEGER NOT NULL DEFAULT nextval('teamfights_players_id_seq'::regclass),
    "teamfight_id" INTEGER,
    "match_player_detail_id" INTEGER,
    "buyback" INTEGER,
    "damage" INTEGER,
    "deaths" INTEGER,
    "gold_delta" INTEGER,
    "xp_start" INTEGER,
    "xp_end" INTEGER,

    CONSTRAINT "teamfights_players_pk" PRIMARY KEY ("id")
);

-- CreateIndex
CREATE INDEX "idx_match_id_player_id" ON "matches_players_details"("match_id", "player_slot", "id");

-- CreateIndex
CREATE INDEX "teamfights_match_id_start_teamfight_id_idx" ON "teamfights"("match_id", "start_teamfight", "id");

-- AddForeignKey
ALTER TABLE "ability_upgrades" ADD CONSTRAINT "ability_upgrades_abilities_id_fk" FOREIGN KEY ("ability_id") REFERENCES "abilities"("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- AddForeignKey
ALTER TABLE "ability_upgrades" ADD CONSTRAINT "ability_upgrades_matches_players_details_id_fk" FOREIGN KEY ("match_player_detail_id") REFERENCES "matches_players_details"("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- AddForeignKey
ALTER TABLE "chats" ADD CONSTRAINT "chats_matches_players_details_id_fk" FOREIGN KEY ("match_player_detail_id") REFERENCES "matches_players_details"("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- AddForeignKey
ALTER TABLE "game_objectives" ADD CONSTRAINT "game_objectives_matches_players_details_id_fk" FOREIGN KEY ("match_player_detail_id_1") REFERENCES "matches_players_details"("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- AddForeignKey
ALTER TABLE "game_objectives" ADD CONSTRAINT "game_objectives_matches_players_details_id_fk_2" FOREIGN KEY ("match_player_detail_id_2") REFERENCES "matches_players_details"("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- AddForeignKey
ALTER TABLE "matches" ADD CONSTRAINT "matches_cluster_regions_id_fk" FOREIGN KEY ("cluster_region_id") REFERENCES "cluster_regions"("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- AddForeignKey
ALTER TABLE "matches_players_details" ADD CONSTRAINT "matches_players_details_heroes_id_fk" FOREIGN KEY ("hero_id") REFERENCES "heroes"("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- AddForeignKey
ALTER TABLE "matches_players_details" ADD CONSTRAINT "matches_players_details_items_id_fk" FOREIGN KEY ("item_id_1") REFERENCES "items"("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- AddForeignKey
ALTER TABLE "matches_players_details" ADD CONSTRAINT "matches_players_details_items_id_fk_2" FOREIGN KEY ("item_id_2") REFERENCES "items"("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- AddForeignKey
ALTER TABLE "matches_players_details" ADD CONSTRAINT "matches_players_details_items_id_fk_3" FOREIGN KEY ("item_id_3") REFERENCES "items"("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- AddForeignKey
ALTER TABLE "matches_players_details" ADD CONSTRAINT "matches_players_details_items_id_fk_4" FOREIGN KEY ("item_id_4") REFERENCES "items"("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- AddForeignKey
ALTER TABLE "matches_players_details" ADD CONSTRAINT "matches_players_details_items_id_fk_5" FOREIGN KEY ("item_id_5") REFERENCES "items"("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- AddForeignKey
ALTER TABLE "matches_players_details" ADD CONSTRAINT "matches_players_details_items_id_fk_6" FOREIGN KEY ("item_id_6") REFERENCES "items"("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- AddForeignKey
ALTER TABLE "matches_players_details" ADD CONSTRAINT "matches_players_details_matches_id_fk" FOREIGN KEY ("match_id") REFERENCES "matches"("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- AddForeignKey
ALTER TABLE "matches_players_details" ADD CONSTRAINT "matches_players_details_players_id_fk" FOREIGN KEY ("player_id") REFERENCES "players"("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- AddForeignKey
ALTER TABLE "player_actions" ADD CONSTRAINT "player_actions_matches_players_details_id_fk" FOREIGN KEY ("match_player_detail_id") REFERENCES "matches_players_details"("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- AddForeignKey
ALTER TABLE "player_ratings" ADD CONSTRAINT "player_ratings_players_id_fk" FOREIGN KEY ("player_id") REFERENCES "players"("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- AddForeignKey
ALTER TABLE "player_times" ADD CONSTRAINT "player_times_matches_players_details_id_fk" FOREIGN KEY ("match_player_detail_id") REFERENCES "matches_players_details"("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- AddForeignKey
ALTER TABLE "purchase_logs" ADD CONSTRAINT "purchase_logs_items_id_fk" FOREIGN KEY ("item_id") REFERENCES "items"("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- AddForeignKey
ALTER TABLE "purchase_logs" ADD CONSTRAINT "purchase_logs_matches_players_details_id_fk" FOREIGN KEY ("match_player_detail_id") REFERENCES "matches_players_details"("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- AddForeignKey
ALTER TABLE "teamfights" ADD CONSTRAINT "teamfights_matches_id_fk" FOREIGN KEY ("match_id") REFERENCES "matches"("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- AddForeignKey
ALTER TABLE "teamfights_players" ADD CONSTRAINT "teamfights_players_matches_players_details_id_fk" FOREIGN KEY ("match_player_detail_id") REFERENCES "matches_players_details"("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- AddForeignKey
ALTER TABLE "teamfights_players" ADD CONSTRAINT "teamfights_players_teamfights_id_fk" FOREIGN KEY ("teamfight_id") REFERENCES "teamfights"("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
