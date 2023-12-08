package com.ggwp.searchservice.match.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QParticipant is a Querydsl query type for Participant
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QParticipant extends EntityPathBase<Participant> {

    private static final long serialVersionUID = 84424027L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QParticipant participant = new QParticipant("participant");

    public final NumberPath<Integer> assists = createNumber("assists", Integer.class);

    public final NumberPath<Integer> champExperience = createNumber("champExperience", Integer.class);

    public final NumberPath<Integer> championId = createNumber("championId", Integer.class);

    public final StringPath championName = createString("championName");

    public final NumberPath<Integer> champLevel = createNumber("champLevel", Integer.class);

    public final NumberPath<Integer> deaths = createNumber("deaths", Integer.class);

    public final NumberPath<Integer> item0 = createNumber("item0", Integer.class);

    public final NumberPath<Integer> item1 = createNumber("item1", Integer.class);

    public final NumberPath<Integer> item2 = createNumber("item2", Integer.class);

    public final NumberPath<Integer> item3 = createNumber("item3", Integer.class);

    public final NumberPath<Integer> item4 = createNumber("item4", Integer.class);

    public final NumberPath<Integer> item5 = createNumber("item5", Integer.class);

    public final NumberPath<Integer> item6 = createNumber("item6", Integer.class);

    public final NumberPath<Integer> kills = createNumber("kills", Integer.class);

    public final NumberPath<Integer> neutralMinionsKilled = createNumber("neutralMinionsKilled", Integer.class);

    public final NumberPath<Integer> participantId = createNumber("participantId", Integer.class);

    public final NumberPath<Long> participantPk = createNumber("participantPk", Long.class);

    public final StringPath primaryDescription = createString("primaryDescription");

    public final StringPath primaryStyle = createString("primaryStyle");

    public final NumberPath<Integer> profileIcon = createNumber("profileIcon", Integer.class);

    public final StringPath puuid = createString("puuid");

    public final StringPath riotIdTagline = createString("riotIdTagline");

    public final StringPath subDescription = createString("subDescription");

    public final StringPath subStyle = createString("subStyle");

    public final NumberPath<Integer> summoner1Id = createNumber("summoner1Id", Integer.class);

    public final NumberPath<Integer> summoner2Id = createNumber("summoner2Id", Integer.class);

    public final StringPath summonerId = createString("summonerId");

    public final NumberPath<Integer> summonerLevel = createNumber("summonerLevel", Integer.class);

    public final StringPath summonerName = createString("summonerName");

    public final QTeam team;

    public final StringPath teamPosition = createString("teamPosition");

    public final NumberPath<Integer> totalDamageDealtToChampions = createNumber("totalDamageDealtToChampions", Integer.class);

    public final NumberPath<Integer> totalDamageTaken = createNumber("totalDamageTaken", Integer.class);

    public final NumberPath<Integer> totalMinionsKilled = createNumber("totalMinionsKilled", Integer.class);

    public QParticipant(String variable) {
        this(Participant.class, forVariable(variable), INITS);
    }

    public QParticipant(Path<? extends Participant> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QParticipant(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QParticipant(PathMetadata metadata, PathInits inits) {
        this(Participant.class, metadata, inits);
    }

    public QParticipant(Class<? extends Participant> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.team = inits.isInitialized("team") ? new QTeam(forProperty("team"), inits.get("team")) : null;
    }

}

