package com.mindbridge.entity;

import com.mindbridge.entity.enums.MileageType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name ="mileage_transaction")
public class MileageTransactionEntity extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userId;

    private int amount;

    @Enumerated(EnumType.STRING)
    private MileageType mileageType;

    private String description;
}