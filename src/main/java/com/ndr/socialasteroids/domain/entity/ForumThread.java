package com.ndr.socialasteroids.domain.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

@Data
@Entity
@Table(name = "threads")
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class, 
    property = "id")
public class ForumThread
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne(optional = false)
    private User owner;
    
    @OneToMany(mappedBy = "thread", fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<Post>();

    @Column(name = "created_on", nullable = false)
    private Instant createdOn;

    @Column(name = "updated_on", nullable = false)
    private Instant updatedOn;

    @Column(name = "deleted", nullable = false)
    public Boolean deleted;

    public ForumThread(){}

    public ForumThread(String title, User owner)
    {
        this.createdOn = Instant.now();
        this.updatedOn = this.createdOn;
        this.title = title;
        this.owner = owner;
        this.deleted = false;
    }

    public void addPost(Post post)
    {
        this.posts.add(post);
    }   
}
