/* Copyright 2016 Danish Maritime Authority.
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
package net.maritimecloud.identityregistry.model.database.entities;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import net.maritimecloud.identityregistry.model.database.Certificate;
import net.maritimecloud.identityregistry.model.database.CertificateModel;

/**
 * Model object representing a device
 */

@Entity
@Table(name = "devices")
public class Device extends CertificateModel {

    public Device() {
    }

    @Column(name = "id_organization")
    private Long idOrganization;

    @Column(name = "device_org_id")
    private String deviceOrgId;

    @Column(name = "name")
    private String name;

    @Column(name = "mrn")
    private String mrn;

    @Column(name = "permissions")
    private String permissions;

    @OneToMany(mappedBy = "device")
    //@Where(clause="UTC_TIMESTAMP() BETWEEN start AND end")
    private List<Certificate> certificates;

    /** Copies this organization into the other */
    public Device copyTo(Device device) {
        Objects.requireNonNull(device);
        device.setId(id);
        device.setIdOrganization(idOrganization);
        device.setName(name);
        device.setDeviceOrgId(deviceOrgId);
        device.setMrn(mrn);
        device.setPermissions(permissions);
        device.getCertificates().clear();
        device.getCertificates().addAll(certificates);
        device.setChildIds();
        return device;
    }

    /** Copies this device into the other
     * Only update things that are allowed to change on update */
    public Device selectiveCopyTo(Device device) {
        device.setName(name);
        device.setDeviceOrgId(deviceOrgId);
        device.setMrn(mrn);
        device.setPermissions(permissions);
        device.setChildIds();
        return device;
    }

    protected void assignToCert(Certificate cert){
        cert.setDevice(this);
    }

    /******************************/
    /** Getters and setters      **/
    /******************************/
    public Long getIdOrganization() {
        return idOrganization;
    }

    public void setIdOrganization(Long idOrganization) {
        this.idOrganization = idOrganization;
    }

    public String getDeviceOrgId() {
        return deviceOrgId;
    }

    public void setDeviceOrgId(String deviceOrgId) {
        this.deviceOrgId = deviceOrgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMrn() {
        return mrn;
    }

    public void setMrn(String mrn) {
        this.mrn = mrn;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public List<Certificate> getCertificates() {
        return certificates;
    }
}
