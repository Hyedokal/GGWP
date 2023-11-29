package com.ggwp.announceservice.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAnnounce is a Querydsl query type for Announce
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAnnounce extends EntityPathBase<Announce> {

    private static final long serialVersionUID = -2147050358L;

    public static final QAnnounce announce = new QAnnounce("announce");

    public final StringPath aContent = createString("aContent");

    public final NumberPath<Long> aId = createNumber("aId", Long.class);

    public final StringPath aTitle = createString("aTitle");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QAnnounce(String variable) {
        super(Announce.class, forVariable(variable));
    }

    public QAnnounce(Path<? extends Announce> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAnnounce(PathMetadata metadata) {
        super(Announce.class, metadata);
    }

}

