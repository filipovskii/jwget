package com.filipovskii.jwget.downloadresult;

enum StatusNames {
  CANCELLED("CANCELLED"), FAILED("FAILED"), IN_PROGRESS("IN_PROGRESS"),
  NOT_STARTED("NOT_STARTED"), SUCCEED("SUCCEED");

  private final String statusName;

  StatusNames(String statusName) {
    this.statusName = statusName;
  }

  public String value() {
    return statusName;
  }
}
