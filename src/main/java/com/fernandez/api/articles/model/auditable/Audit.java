package com.fernandez.api.articles.model.auditable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

@Getter
@Setter
@Embeddable
@RequiredArgsConstructor
public class Audit {

    @Column ( name = "created_on" )
    private Date createdOn;

    @Column ( name = "updated_on" )
    private Date updatedOn;

    @PrePersist
    public void prePersist ( ) {
        createdOn = new Date ( );
    }

    @PreUpdate
    public void preUpdate ( ) {
        updatedOn = new Date ( );
    }

}
