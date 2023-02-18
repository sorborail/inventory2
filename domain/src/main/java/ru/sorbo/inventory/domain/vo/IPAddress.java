package ru.sorbo.inventory.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class IPAddress {
  private String ipAddress;
  private Protocol protocol;

  public IPAddress(String ipAddress) {
    if(ipAddress == null)
      throw new IllegalArgumentException("Null IP address");
    this.ipAddress = ipAddress;
    if(ipAddress.length()<=15) {
      this.protocol = Protocol.IPV4;
    } else {
      this.protocol = Protocol.IPV6;
    }
  }

  public static IPAddress fromAddress(String ipAddress) {
    return new IPAddress(ipAddress);
  }
}
