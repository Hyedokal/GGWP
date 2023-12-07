package com.ggwp.searchservice.match.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMatchSummoner is a Querydsl query type for MatchSummoner
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMatchSummoner extends EntityPathBase<MatchSummoner> {

    private static final long serialVersionUID = 1831771739L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMatchSummoner matchSummoner = new QMatchSummoner("matchSummoner");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMatch match;

    public final com.ggwp.searchservice.summoner.domain.QSummoner summoner;

    public QMatchSummoner(String variable) {
        this(MatchSummoner.class, forVariable(variable), INITS);
    }

    public QMatchSummoner(Path<? extends MatchSummoner> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMatchSummoner(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMatchSummoner(PathMetadata metadata, PathInits inits) {
        this(MatchSummoner.class, metadata, inits);
    }

    public QMatchSummoner(Class<? extends MatchSummoner> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.match = inits.isInitialized("match") ? new QMatch(forProperty("match")) : null;
        this.summoner = inits.isInitialized("summoner") ? new com.ggwp.searchservice.summoner.domain.QSummoner(forProperty("summoner"), inits.get("summoner")) : null;
    }

}

