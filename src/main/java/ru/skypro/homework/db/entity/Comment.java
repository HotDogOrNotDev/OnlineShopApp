package ru.skypro.homework.db.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;                     // Уникальный идентификатор комментария
    @Column(name = "created_at")
    private Date createdAt;                 // Дата и время создания комментария
    private String text;                    // Текст комментария
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;                      // Пользователь, оставивший комментарий
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ad_id")
    private Ad ad;                          // Объявление, к которому относится этот комментарий

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment comment)) return false;
        return pk != null && pk.equals(comment.pk);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Comment{" +
                "pk=" + pk +
                ", createdAt=" + createdAt +
                ", text='" + text + '\'' +
                ", user=" + user +
                ", ad=" + ad +
                '}';
    }

}
