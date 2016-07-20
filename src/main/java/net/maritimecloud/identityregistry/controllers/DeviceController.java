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
package net.maritimecloud.identityregistry.controllers;

import net.maritimecloud.identityregistry.services.EntityService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import net.maritimecloud.identityregistry.exception.McBasicRestException;
import net.maritimecloud.identityregistry.model.database.Certificate;
import net.maritimecloud.identityregistry.model.data.CertificateRevocation;
import net.maritimecloud.identityregistry.model.data.PemCertificate;
import net.maritimecloud.identityregistry.model.database.entities.Device;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class DeviceController extends EntityController<Device> {

    @Autowired
    public void setEntityService(EntityService<Device> entityService) {
        this.entityService = entityService;
    }

    /**
     * Creates a new Device
     * 
     * @return a reply...
     * @throws McBasicRestException 
     */ 
    @RequestMapping(
            value = "/api/org/{orgShortName}/device",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    @PreAuthorize("hasRole('ORG_ADMIN') and @accessControlUtil.hasAccessToOrg(#orgShortName)")
    public ResponseEntity<Device> createDevice(HttpServletRequest request, @PathVariable String orgShortName, @RequestBody Device input) throws McBasicRestException {
        return this.createEntity(request, orgShortName, input);
    }

    /**
     * Returns info about the device identified by the given ID
     * 
     * @return a reply...
     * @throws McBasicRestException 
     */
    @RequestMapping(
            value = "/api/org/{orgShortName}/device/{deviceId}",
            method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    @PreAuthorize("@accessControlUtil.hasAccessToOrg(#orgShortName)")
    public ResponseEntity<Device> getDevice(HttpServletRequest request, @PathVariable String orgShortName, @PathVariable Long deviceId) throws McBasicRestException {
        return this.getEntity(request, orgShortName, deviceId);
    }

    /**
     * Updates a Device
     * 
     * @return a reply...
     * @throws McBasicRestException 
     */
    @RequestMapping(
            value = "/api/org/{orgShortName}/device/{deviceId}",
            method = RequestMethod.PUT)
    @ResponseBody
    @PreAuthorize("hasRole('ORG_ADMIN') and @accessControlUtil.hasAccessToOrg(#orgShortName)")
    public ResponseEntity<?> updateDevice(HttpServletRequest request, @PathVariable String orgShortName, @PathVariable Long deviceId, @RequestBody Device input) throws McBasicRestException {
        return this.updateEntity(request, orgShortName, deviceId, input);
    }

    /**
     * Deletes a Device
     * 
     * @return a reply...
     * @throws McBasicRestException 
     */
    @RequestMapping(
            value = "/api/org/{orgShortName}/device/{deviceId}",
            method = RequestMethod.DELETE)
    @ResponseBody
    @PreAuthorize("hasRole('ORG_ADMIN') and @accessControlUtil.hasAccessToOrg(#orgShortName)")
    public ResponseEntity<?> deleteDevice(HttpServletRequest request, @PathVariable String orgShortName, @PathVariable Long deviceId) throws McBasicRestException {
        return this.deleteEntity(request, orgShortName, deviceId);
    }

    /**
     * Returns a list of devices owned by the organization identified by the given ID
     * 
     * @return a reply...
     * @throws McBasicRestException 
     */
    @RequestMapping(
            value = "/api/org/{orgShortName}/devices",
            method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @PreAuthorize("@accessControlUtil.hasAccessToOrg(#orgShortName)")
    public ResponseEntity<List<Device>> getOrganizationDevices(HttpServletRequest request, @PathVariable String orgShortName) throws McBasicRestException {
        return this.getOrganizationEntities(request, orgShortName);
    }

    /**
     * Returns new certificate for the device identified by the given ID
     * 
     * @return a reply...
     * @throws McBasicRestException 
     */
    @RequestMapping(
            value = "/api/org/{orgShortName}/device/{deviceId}/generatecertificate",
            method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @PreAuthorize("hasRole('ORG_ADMIN') and @accessControlUtil.hasAccessToOrg(#orgShortName)")
    public ResponseEntity<PemCertificate> newDeviceCert(HttpServletRequest request, @PathVariable String orgShortName, @PathVariable Long deviceId) throws McBasicRestException {
        return this.newEntityCert(request, orgShortName, deviceId, "device");
    }

    /**
     * Revokes certificate for the device identified by the given ID
     * 
     * @return a reply...
     * @throws McBasicRestException 
     */
    @RequestMapping(
            value = "/api/org/{orgShortName}/device/{deviceId}/certificates/{certId}/revoke",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @PreAuthorize("hasRole('ORG_ADMIN') and @accessControlUtil.hasAccessToOrg(#orgShortName)")
    public ResponseEntity<?> revokeDeviceCert(HttpServletRequest request, @PathVariable String orgShortName, @PathVariable Long deviceId, @PathVariable Long certId,  @RequestBody CertificateRevocation input) throws McBasicRestException {
        return this.revokeEntityCert(request, orgShortName, deviceId, certId, input);
    }

    @Override
    protected Device getCertEntity(Certificate cert) {
        return cert.getDevice();
    }


}

