package ru.sorbo.inventory.framework.adapters.output.h2.mappers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ru.sorbo.inventory.domain.entity.CoreRouter;
import ru.sorbo.inventory.domain.entity.EdgeRouter;
import ru.sorbo.inventory.domain.entity.Router;
import ru.sorbo.inventory.domain.entity.Switch;
import ru.sorbo.inventory.domain.entity.factory.RouterFactory;
import ru.sorbo.inventory.domain.vo.IPAddress;
import ru.sorbo.inventory.domain.vo.Identifier;
import ru.sorbo.inventory.domain.vo.Location;
import ru.sorbo.inventory.domain.vo.Model;
import ru.sorbo.inventory.domain.vo.Network;
import ru.sorbo.inventory.domain.vo.RouterType;
import ru.sorbo.inventory.domain.vo.SwitchType;
import ru.sorbo.inventory.domain.vo.Vendor;
import ru.sorbo.inventory.framework.adapters.output.h2.data.IPAddressData;
import ru.sorbo.inventory.framework.adapters.output.h2.data.LocationData;
import ru.sorbo.inventory.framework.adapters.output.h2.data.ModelData;
import ru.sorbo.inventory.framework.adapters.output.h2.data.NetworkData;
import ru.sorbo.inventory.framework.adapters.output.h2.data.RouterData;
import ru.sorbo.inventory.framework.adapters.output.h2.data.RouterTypeData;
import ru.sorbo.inventory.framework.adapters.output.h2.data.SwitchData;
import ru.sorbo.inventory.framework.adapters.output.h2.data.SwitchTypeData;
import ru.sorbo.inventory.framework.adapters.output.h2.data.VendorData;

public class RouterH2Mapper {
	
	public static Router routerDataToDomain(RouterData routerData) {
    var router = RouterFactory.getRouter(
            Identifier.withId(routerData.getRouterId().toString()),
            Vendor.valueOf(routerData.getRouterVendor().toString()),
            Model.valueOf(routerData.getRouterModel().toString()),
            IPAddress.fromAddress(routerData.getIp().getAddress()),
            locationDataToLocation(routerData.getRouterLocation()),
            RouterType.valueOf(routerData.getRouterType().name()));
    if(routerData.getRouterType().equals(RouterTypeData.CORE)) {
        var coreRouter = (CoreRouter) router;
        coreRouter.setRouters(getRoutersFromData(routerData.getRouters()));
        return coreRouter;
    } else {
        var edgeRouter = (EdgeRouter) router;
        edgeRouter.setSwitches(getSwitchesFromData(routerData.getSwitches()));
        return edgeRouter;
    }
}

public static RouterData routerDomainToData(Router router) {
    var routerData = RouterData.builder().
            routerId(router.getId().getUuid()).
            routerVendor(VendorData.valueOf(router.getVendor().toString())).
            routerModel(ModelData.valueOf(router.getModel().toString())).
            ip(IPAddressData.fromAddress(router.getIp().getIpAddress())).
            routerLocation(locationDomainToLocationData(router.getLocation())).
            routerType(RouterTypeData.valueOf(router.getRouterType().toString())).
            build();
    if(router.getRouterType().equals(RouterType.CORE)) {
        var coreRouter = (CoreRouter) router;
        routerData.setRouters(getRoutersFromDomain(coreRouter.getRouters()));
    } else {
        var edgeRouter = (EdgeRouter) router;
        routerData.setSwitches(getSwitchesFromDomain(edgeRouter.getSwitches()));
    }
    return routerData;
}

public static Switch switchDataToDomain(SwitchData switchData) {
    return Switch.builder().
            switchId(Identifier.withId(switchData.getSwitchId().toString())).
            routerId(Identifier.withId(switchData.getRouterId().toString())).
            vendor(Vendor.valueOf(switchData.getSwitchVendor().toString())).
            model(Model.valueOf(switchData.getSwitchModel().toString())).
            ip(IPAddress.fromAddress(switchData.getIp().getAddress())).
            location(locationDataToLocation(switchData.getSwitchLocation())).
            switchType(SwitchType.valueOf(switchData.getSwitchType().toString())).
            switchNetworks(getNetworksFromData(switchData.getNetworks())).
            build();
}

public static SwitchData switchDomainToData(Switch aSwitch){
    return  SwitchData.builder().
            switchId(aSwitch.getId().getUuid()).
            routerId(aSwitch.getRouterId().getUuid()).
            switchVendor(VendorData.valueOf(aSwitch.getVendor().toString())).
            switchModel(ModelData.valueOf(aSwitch.getModel().toString())).
            ip(IPAddressData.fromAddress(aSwitch.getIp().getIpAddress())).
            switchLocation(locationDomainToLocationData(aSwitch.getLocation())).
            switchType(SwitchTypeData.valueOf(aSwitch.getSwitchType().toString())).
            networks(getNetworksFromDomain(aSwitch.getSwitchNetworks(), aSwitch.getId().getUuid())).
            build();
}

public static Location locationDataToLocation(LocationData locationData) {
    return Location.builder()
            .address(locationData.getAddress())
            .city(locationData.getCity())
            .state(locationData.getState())
            .zipCode(locationData.getZipcode())
            .country(locationData.getCountry())
            .latitude(locationData.getLatitude())
            .longitude(locationData.getLongitude())
            .build();
}

public static LocationData locationDomainToLocationData(Location location) {
    return LocationData.builder()
            .address(location.getAddress())
            .city(location.getCity())
            .state(location.getState())
            .zipcode(location.getZipCode())
            .country(location.getCountry())
            .latitude(location.getLatitude())
            .longitude(location.getLongitude())
            .build();
}

private static Map<Identifier, Router> getRoutersFromData(List<RouterData> routerDataList) {
    Map<Identifier, Router> routerMap = new HashMap<>();
    for (RouterData routerData : routerDataList) {
        routerMap.put(
                Identifier.withId(routerData.getRouterId().toString()),
                routerDataToDomain(routerData));
    }
    return routerMap;
}

private static List<RouterData>  getRoutersFromDomain(Map<Identifier, Router> routers) {
    List<RouterData> routerDataList = new ArrayList<>();
     routers.values().stream().forEach(router -> {
         var routerData = routerDomainToData(router);
         routerDataList.add(routerData);
     });
    return routerDataList;
}

private static Map<Identifier, Switch> getSwitchesFromData(List<SwitchData> switchDataList) {
    Map<Identifier, Switch> switchMap = new HashMap<>();
    for (SwitchData switchData : switchDataList) {
        switchMap.put(
                Identifier.withId(switchData.getSwitchId().toString()),
                switchDataToDomain(switchData));
    }
    return switchMap;
}

private static List<SwitchData>  getSwitchesFromDomain(Map<Identifier, Switch> switches) {
    List<SwitchData> switchDataList = new ArrayList<>();
    if(switches!=null) {
        switches.values().stream().forEach(aSwitch -> {
            switchDataList.add(switchDomainToData(aSwitch));
        });
    }
    return switchDataList;
}

private static List<Network> getNetworksFromData(List<NetworkData> networkData) {
    List<Network> networks = new ArrayList<>();
    networkData.forEach(data ->{
        var network = new Network(
                IPAddress.fromAddress(data.getIp().getAddress()),
                data.getName(),
                data.getCidr());
        networks.add(network);
    });
    return networks;
}

private static List<NetworkData>  getNetworksFromDomain(List<Network> networks, UUID routerId) {
    List<NetworkData> networkDataList = new ArrayList<>();
    if(networks!=null) {
        networks.forEach(network -> {
            var networkData = new NetworkData(
                    routerId,
                    IPAddressData.fromAddress(network.getNetworkAddress().getIpAddress()),
                    network.getNetworkName(),
                    network.getNetworkCidr()
            );
            networkDataList.add(networkData);
        });
    }
    return networkDataList;
}
}
