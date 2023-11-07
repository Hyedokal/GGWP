package com.ggwp.searchservice.data.item.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id; // PK

    private String itemName; // 아이템 이름

    @Lob
    private byte[] itemImg; // 이미지

    private String itemDescription; // 아이템 설명

    private int itemGold; // 아이템 가격

}
