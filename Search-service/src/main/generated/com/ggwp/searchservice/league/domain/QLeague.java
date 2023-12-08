package com.ggwp.searchservice.league.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLeague is a Querydsl query type for League
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLeague extends EntityPathBase<League> {

    private static final long serialVersionUID = -976634013L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLeague league = new QLeague("league");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath leagueId = createString("leagueId");

    public final NumberPath<Integer> leaguePoints = createNumber("leaguePoints", Integer.class);

    public final NumberPath<Integer> losses = createNumber("losses", Integer.class);

    public final StringPath queueType = createString("queueType");

    public final StringPath ranks = createString("ranks");

    public final com.ggwp.searchservice.summoner.domain.QSummoner summoner;

    public final StringPath tier = createString("tier");

    public final NumberPath<Integer> wins = createNumber("wins", Integer.class);

    public QLeague(String variable) {
        this(League.class, forVariable(variable), INITS);
    }

    public QLeague(Path<? extends League> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLeague(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLeague(PathMetadata metadata, PathInits inits) {
        this(League.class, metadata, inits);
    }

    public QLeague(Class<? extends League> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.summoner = inits.isInitialized("summoner") ? new com.ggwp.searchservice.summoner.domain.QSummoner(forProperty("summoner"), inits.get("summoner")) : null;
    }

}

