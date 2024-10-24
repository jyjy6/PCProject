package org.iclass.PCProject.statistics;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseHistory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer seq;

  @Column(nullable = false)
  private String vendor;

  @Column(nullable = false)
  private String code;

  @Column(nullable = false)
  private String username;

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime regdate;

  @Column(nullable = false)
  private Integer price;

  @Column(nullable = false)
  private Integer count;
}
