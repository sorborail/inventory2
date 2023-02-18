package ru.sorbo.inventory.framework.adapters.output.h2.data;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.*;
import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.Converter;

import java.util.List;
import java.util.UUID;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "routers")
@MappedSuperclass
@Converter(name="uuidConverter", converterClass= UUIDTypeConverter.class)
public class RouterData implements Serializable {
	@Id
  @Column(name="router_id",
          columnDefinition = "uuid",
          updatable = false )
  @Convert("uuidConverter")
  private UUID routerId;

  @Column(name="router_parent_core_id")
  @Convert("uuidConverter")
  private UUID routerParentCoreId;

  @Embedded
  @Enumerated(EnumType.STRING)
  @Column(name="router_vendor")
  private VendorData routerVendor;

  @Embedded
  @Enumerated(EnumType.STRING)
  @Column(name="router_model")
  private ModelData routerModel;

  @Embedded
  @AttributeOverride(name = "address", column = @Column(name = "router_ip_address"))
  @AttributeOverride(name = "protocol", column = @Column(name = "router_ip_protocol"))
  private IPAddressData ip;

  @ManyToOne
  @JoinColumn(name="location_id")
  private LocationData routerLocation;

  @Embedded
  @Enumerated(EnumType.STRING)
  @Column(name="router_type")
  private RouterTypeData routerType;

  @OneToMany
  @JoinColumn(table = "switches",
          name = "router_id",
          referencedColumnName = "router_id")
  @Setter
  private List<SwitchData> switches;

  @OneToMany
  @JoinTable(name="routers",
          joinColumns={@JoinColumn(name="router_parent_core_id")},
          inverseJoinColumns={@JoinColumn(name="router_id")})
  @Setter
  private List<RouterData> routers;

}
