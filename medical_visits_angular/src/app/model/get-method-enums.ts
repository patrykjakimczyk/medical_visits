export enum Sort {
  ASC,
  DESC
}

export enum SortProperty {
  LAST_NAME = "last_name",
  FIRST_NAME = "first_name"
}

export enum FilterTypeParam {
  FIRST_NAME = "firstName",
  LAST_NAME = "lastName",
  PESEL = "pesel"
}

export enum FilterTypeName {
  FIRST_NAME = "First name",
  LAST_NAME = "Last name",
  PESEL = "Pesel"
}

export const FILTER_TYPES = [
  {
    param: FilterTypeParam.FIRST_NAME,
    name: FilterTypeName.FIRST_NAME
  },
  {
    param: FilterTypeParam.LAST_NAME,
    name: FilterTypeName.LAST_NAME
  },
  {
    param: FilterTypeParam.PESEL,
    name: FilterTypeName.PESEL
  }
]
