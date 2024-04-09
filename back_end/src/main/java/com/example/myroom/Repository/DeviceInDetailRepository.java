package com.example.myroom.Repository;


import com.example.myroom.Entities.DeviceInDetail;
import com.example.myroom.Projection.DeviceCollapseProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeviceInDetailRepository extends JpaRepository<DeviceInDetail, Integer> {

    @Query(value = "SELECT device_in_detail.id, device_in_detail.name, device_in_detail.status  FROM device_in_detail", nativeQuery = true)
    public List<DeviceCollapseProjection> getListCollapse();
    public DeviceInDetail findDeviceInDetailByPinAndAndDeviceURLAndAndDevicePort(String pin, String url, String port);
    public List<DeviceInDetail> findAllByRoomId(String roomId);
}
