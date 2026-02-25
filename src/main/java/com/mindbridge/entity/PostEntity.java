package com.mindbridge.entity;

import com.mindbridge.entity.enums.Category;
import com.mindbridge.entity.user.BaseEntity;
import com.mindbridge.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "post")
public class PostEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "contents", nullable = false, columnDefinition = "TEXT")
    private String contents;

    @Column(name = "is_anonymous")
    private Boolean isAnonymous;

    @Column(name = "view_count")
    private int viewCount;

    @Setter
    @Column(name = "like_count")
    private int likeCount;

    @Setter
    @Column(name = "comment_count")
    private int commentCount;

    public void update(String title, String contents) {
        if (title != null) {
            this.title = title;
        }
        if (contents != null) {
            this.contents = contents;
        }
    }

    public void increaseViewCount() {
        this.viewCount++;
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }

}