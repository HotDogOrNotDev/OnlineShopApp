package ru.skypro.homework.db.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ads")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;                 // Уникальный идентификатор объявления
    private Integer price;              // Цена объявления
    private String title;               // Заголовок объявления
    private String description;         // Описание объявления
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;                  // Пользователь, который создал это объявление

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ad ad)) return false;
        return pk != null && pk.equals(ad.pk);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
