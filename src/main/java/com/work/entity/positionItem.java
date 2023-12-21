package com.work.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class positionItem {
   private String position_name;
   private String position_status;
   private Integer position_id;
   private String position_address;
}
