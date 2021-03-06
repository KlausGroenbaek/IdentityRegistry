/*
 * Copyright 2017 Danish Maritime Authority.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package net.maritimecloud.identityregistry.model.database;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.maritimecloud.identityregistry.model.JsonSerializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@ToString
public abstract class TimestampModel implements JsonSerializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    protected Long id;

    @Column(name = "created_at", updatable=false)
    protected Date createdAt;

    @Column(name = "updated_at")
    protected Date updatedAt;

    /** Called at creation, set created_at and updated_at timestamp */
    @PrePersist
    void createdAt() {
        this.createdAt = this.updatedAt = new Date();
    }

    /** Called on update, set updated_at timestamp */
    @PreUpdate
    void updatedAt() {
        this.updatedAt = new Date();
    }

    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    // Override if needed - use to detect if blanking of sensitive fields are needed
    public boolean hasSensitiveFields() {
        return false;
    }

    // Override if needed - use when blanking sensitive fields so that users without privileges
    // can see object with non-sensitive data.
    public void clearSensitiveFields() {
    }
}
