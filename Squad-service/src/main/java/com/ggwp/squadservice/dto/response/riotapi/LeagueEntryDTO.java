package com.ggwp.squadservice.dto.response.riotapi;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LeagueEntryDTO {
    public String queueType;
    public String tier;
    public String rank;
}
