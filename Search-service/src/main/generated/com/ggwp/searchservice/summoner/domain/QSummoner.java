package com.ggwp.searchservice.summoner.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSummoner is a Querydsl query type for Summoner
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSummoner extends EntityPathBase<Summoner> {

    private static final long serialVersionUID = 663182433L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSummoner summoner = new QSummoner("summoner");

    public final com.ggwp.searchservice.account.domain.QAccount account;

    public final StringPath id = createString("id");

    public final ListPath<com.ggwp.searchservice.league.domain.League, com.ggwp.searchservice.league.domain.QLeague> leagues = this.<com.ggwp.searchservice.league.domain.League, com.ggwp.searchservice.league.domain.QLeague>createList("leagues", com.ggwp.searchservice.league.domain.League.class, com.ggwp.searchservice.league.domain.QLeague.class, PathInits.DIRECT2);

    public final ListPath<com.ggwp.searchservice.match.domain.MatchSummoner, com.ggwp.searchservice.match.domain.QMatchSummoner> matchSummoners = this.<com.ggwp.searchservice.match.domain.MatchSummoner, com.ggwp.searchservice.match.domain.QMatchSummoner>createList("matchSummoners", com.ggwp.searchservice.match.domain.MatchSummoner.class, com.ggwp.searchservice.match.domain.QMatchSummoner.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> profileIconId = createNumber("profileIconId", Integer.class);

    public final StringPath puuid = createString("puuid");

    public final NumberPath<Long> revisionDate = createNumber("revisionDate", Long.class);

    public final NumberPath<Integer> summonerLevel = createNumber("summonerLevel", Integer.class);

    public QSummoner(String variable) {
        this(Summoner.class, forVariable(variable), INITS);
    }

    public QSummoner(Path<? extends Summoner> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSummoner(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSummoner(PathMetadata metadata, PathInits inits) {
        this(Summoner.class, metadata, inits);
    }

    public QSummoner(Class<? extends Summoner> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new com.ggwp.searchservice.account.domain.QAccount(forProperty("account"), inits.get("account")) : null;
    }

}

