package com.ggwp.searchservice.match.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMatch is a Querydsl query type for Match
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMatch extends EntityPathBase<Match> {

    private static final long serialVersionUID = -1684939283L;

    public static final QMatch match = new QMatch("match");

    public final NumberPath<Long> gameCreation = createNumber("gameCreation", Long.class);

    public final NumberPath<Long> gameDuration = createNumber("gameDuration", Long.class);

    public final NumberPath<Long> gameEndTimestamp = createNumber("gameEndTimestamp", Long.class);

    public final NumberPath<Long> gameStartTimestamp = createNumber("gameStartTimestamp", Long.class);

    public final StringPath matchId = createString("matchId");

    public final ListPath<MatchSummoner, QMatchSummoner> matchSummoners = this.<MatchSummoner, QMatchSummoner>createList("matchSummoners", MatchSummoner.class, QMatchSummoner.class, PathInits.DIRECT2);

    public final StringPath platformId = createString("platformId");

    public final EnumPath<com.ggwp.searchservice.common.enums.GameMode> queueId = createEnum("queueId", com.ggwp.searchservice.common.enums.GameMode.class);

    public final ListPath<Team, QTeam> teams = this.<Team, QTeam>createList("teams", Team.class, QTeam.class, PathInits.DIRECT2);

    public QMatch(String variable) {
        super(Match.class, forVariable(variable));
    }

    public QMatch(Path<? extends Match> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMatch(PathMetadata metadata) {
        super(Match.class, metadata);
    }

}

