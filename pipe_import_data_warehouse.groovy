{
    "name": "PIPE_DATAPATH_IMPORT_TABLES_PY_LHP_02",
    "objectId": "8ebc5b3d-1d6e-4654-8844-f7c282185e94",
    "properties": {
        "activities": [
            {
                "name": "ForEach_e6s",
                "type": "ForEach",
                "dependsOn": [],
                "typeProperties": {
                    "items": {
                        "value": "@pipeline().parameters.cw_items_e6s",
                        "type": "Expression"
                    },
                    "activities": [
                        {
                            "name": "Copy_e6s",
                            "type": "Copy",
                            "dependsOn": [],
                            "policy": {
                                "timeout": "0.12:00:00",
                                "retry": 0,
                                "retryIntervalInSeconds": 30,
                                "secureOutput": false,
                                "secureInput": false
                            },
                            "typeProperties": {
                                "source": {
                                    "type": "LakehouseTableSource",
                                    "datasetSettings": {
                                        "annotations": [],
                                        "linkedService": {
                                            "name": "LKH_DATAPATH_PY_LHP_01",
                                            "properties": {
                                                "annotations": [],
                                                "type": "Lakehouse",
                                                "typeProperties": {
                                                    "workspaceId": "9b7090d2-dc30-41fd-8589-dd10f9074a97",
                                                    "artifactId": "43d4aaa6-bbf0-43aa-8990-5431b7d5196c",
                                                    "rootFolder": "Tables"
                                                }
                                            }
                                        },
                                        "type": "LakehouseTable",
                                        "schema": [],
                                        "typeProperties": {
                                            "table": {
                                                "value": "@item().source.table",
                                                "type": "Expression"
                                            }
                                        }
                                    }
                                },
                                "sink": {
                                    "type": "DataWarehouseSink",
                                    "allowCopyCommand": true,
                                    "tableOption": {
                                        "value": "@item().copySink.tableOption",
                                        "type": "Expression"
                                    },
                                    "datasetSettings": {
                                        "annotations": [],
                                        "linkedService": {
                                            "name": "DWH_DATAPATH_PY_LHP_01",
                                            "properties": {
                                                "annotations": [],
                                                "type": "DataWarehouse",
                                                "typeProperties": {
                                                    "endpoint": "baop34nx3fluzjgpeh6yuqtaxm-2kihbgzq3t6udbmj3uipsb2ks4.datawarehouse.fabric.microsoft.com",
                                                    "artifactId": "77739124-230f-4d35-98d4-a6f38e022aed",
                                                    "workspaceId": "9b7090d2-dc30-41fd-8589-dd10f9074a97"
                                                }
                                            }
                                        },
                                        "type": "DataWarehouseTable",
                                        "schema": [],
                                        "typeProperties": {
                                            "schema": {
                                                "value": "@item().destination.schema",
                                                "type": "Expression"
                                            },
                                            "table": {
                                                "value": "@item().destination.table",
                                                "type": "Expression"
                                            }
                                        }
                                    }
                                },
                                "enableStaging": true,
                                "translator": {
                                    "value": "@item().copyActivity.translator",
                                    "type": "Expression"
                                }
                            }
                        }
                    ]
                }
            }
        ],
        "parameters": {
            "cw_items_ql4": {
                "type": "Array",
                "defaultValue": [
                    {
                        "source": {
                            "table": "dim_brokers"
                        },
                        "destination": {
                            "schema": "sale",
                            "table": "dim_brokers"
                        },
                        "copyActivity": {
                            "translator": {
                                "type": "TabularTranslator",
                                "mappings": [
                                    {
                                        "source": {
                                            "name": "id_broker",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "id_broker",
                                            "physicalType": "bigint"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "name",
                                            "type": "String",
                                            "physicalType": "string"
                                        },
                                        "sink": {
                                            "name": "name",
                                            "physicalType": "varchar",
                                            "length": "8000"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "region",
                                            "type": "String",
                                            "physicalType": "string"
                                        },
                                        "sink": {
                                            "name": "region",
                                            "physicalType": "varchar",
                                            "length": "8000"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "email",
                                            "type": "String",
                                            "physicalType": "string"
                                        },
                                        "sink": {
                                            "name": "email",
                                            "physicalType": "varchar",
                                            "length": "8000"
                                        }
                                    }
                                ],
                                "typeConversion": true,
                                "typeConversionSettings": {
                                    "allowDataTruncation": true,
                                    "treatBooleanAsNumber": false
                                }
                            }
                        }
                    },
                    {
                        "source": {
                            "table": "dim_campaigns"
                        },
                        "destination": {
                            "schema": "marketing",
                            "table": "dim_campaigns"
                        },
                        "copyActivity": {
                            "translator": {
                                "type": "TabularTranslator",
                                "mappings": [
                                    {
                                        "source": {
                                            "name": "id_campaing",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "id_campaing",
                                            "physicalType": "bigint"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "chanel",
                                            "type": "String",
                                            "physicalType": "string"
                                        },
                                        "sink": {
                                            "name": "chanel",
                                            "physicalType": "varchar",
                                            "length": "8000"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "name",
                                            "type": "String",
                                            "physicalType": "string"
                                        },
                                        "sink": {
                                            "name": "name",
                                            "physicalType": "varchar",
                                            "length": "8000"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "init_date",
                                            "type": "Date",
                                            "physicalType": "date"
                                        },
                                        "sink": {
                                            "name": "init_date",
                                            "physicalType": "date"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "end_date",
                                            "type": "Date",
                                            "physicalType": "date"
                                        },
                                        "sink": {
                                            "name": "end_date",
                                            "physicalType": "date"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "budget",
                                            "type": "Decimal",
                                            "physicalType": "decimal"
                                        },
                                        "sink": {
                                            "name": "budget",
                                            "physicalType": "decimal",
                                            "scale": 4,
                                            "precision": 19
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "Rank_budget",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "Rank_budget",
                                            "physicalType": "bigint"
                                        }
                                    }
                                ],
                                "typeConversion": true,
                                "typeConversionSettings": {
                                    "allowDataTruncation": true,
                                    "treatBooleanAsNumber": false
                                }
                            }
                        }
                    },
                    {
                        "source": {
                            "table": "dim_customers"
                        },
                        "destination": {
                            "schema": "sale",
                            "table": "dim_customers"
                        },
                        "copyActivity": {
                            "translator": {
                                "type": "TabularTranslator",
                                "mappings": [
                                    {
                                        "source": {
                                            "name": "id_customer",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "id_customer",
                                            "physicalType": "bigint"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "name",
                                            "type": "String",
                                            "physicalType": "string"
                                        },
                                        "sink": {
                                            "name": "name",
                                            "physicalType": "varchar",
                                            "length": "8000"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "last_name",
                                            "type": "String",
                                            "physicalType": "string"
                                        },
                                        "sink": {
                                            "name": "last_name",
                                            "physicalType": "varchar",
                                            "length": "8000"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "email",
                                            "type": "String",
                                            "physicalType": "string"
                                        },
                                        "sink": {
                                            "name": "email",
                                            "physicalType": "varchar",
                                            "length": "8000"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "region",
                                            "type": "String",
                                            "physicalType": "string"
                                        },
                                        "sink": {
                                            "name": "region",
                                            "physicalType": "varchar",
                                            "length": "8000"
                                        }
                                    }
                                ],
                                "typeConversion": true,
                                "typeConversionSettings": {
                                    "allowDataTruncation": true,
                                    "treatBooleanAsNumber": false
                                }
                            }
                        }
                    },
                    {
                        "source": {
                            "table": "dim_projects"
                        },
                        "destination": {
                            "schema": "projects",
                            "table": "dim_projects"
                        },
                        "copyActivity": {
                            "translator": {
                                "type": "TabularTranslator",
                                "mappings": [
                                    {
                                        "source": {
                                            "name": "id_project",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "id_project",
                                            "physicalType": "bigint"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "name",
                                            "type": "String",
                                            "physicalType": "string"
                                        },
                                        "sink": {
                                            "name": "name",
                                            "physicalType": "varchar",
                                            "length": "8000"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "city",
                                            "type": "String",
                                            "physicalType": "string"
                                        },
                                        "sink": {
                                            "name": "city",
                                            "physicalType": "varchar",
                                            "length": "8000"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "region",
                                            "type": "String",
                                            "physicalType": "string"
                                        },
                                        "sink": {
                                            "name": "region",
                                            "physicalType": "varchar",
                                            "length": "8000"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "release_date",
                                            "type": "Date",
                                            "physicalType": "date"
                                        },
                                        "sink": {
                                            "name": "release_date",
                                            "physicalType": "date"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "status",
                                            "type": "String",
                                            "physicalType": "string"
                                        },
                                        "sink": {
                                            "name": "status",
                                            "physicalType": "varchar",
                                            "length": "8000"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "year_release_date",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "year_release_date",
                                            "physicalType": "bigint"
                                        }
                                    }
                                ],
                                "typeConversion": true,
                                "typeConversionSettings": {
                                    "allowDataTruncation": true,
                                    "treatBooleanAsNumber": false
                                }
                            }
                        }
                    },
                    {
                        "source": {
                            "table": "fac_leads"
                        },
                        "destination": {
                            "schema": "marketing",
                            "table": "fac_leads"
                        },
                        "copyActivity": {
                            "translator": {
                                "type": "TabularTranslator",
                                "mappings": [
                                    {
                                        "source": {
                                            "name": "id_lead",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "id_lead",
                                            "physicalType": "bigint"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "id_customer",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "id_customer",
                                            "physicalType": "bigint"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "id_property",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "id_property",
                                            "physicalType": "bigint"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "id_campaing",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "id_campaing",
                                            "physicalType": "bigint"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "init_date",
                                            "type": "Date",
                                            "physicalType": "date"
                                        },
                                        "sink": {
                                            "name": "init_date",
                                            "physicalType": "date"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "chanel",
                                            "type": "String",
                                            "physicalType": "string"
                                        },
                                        "sink": {
                                            "name": "chanel",
                                            "physicalType": "varchar",
                                            "length": "8000"
                                        }
                                    }
                                ],
                                "typeConversion": true,
                                "typeConversionSettings": {
                                    "allowDataTruncation": true,
                                    "treatBooleanAsNumber": false
                                }
                            }
                        }
                    },
                    {
                        "source": {
                            "table": "fac_sales"
                        },
                        "destination": {
                            "schema": "sales",
                            "table": "fac_sales"
                        },
                        "copyActivity": {
                            "translator": {
                                "type": "TabularTranslator",
                                "mappings": [
                                    {
                                        "source": {
                                            "name": "id_sale",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "id_sale",
                                            "physicalType": "bigint"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "id_property",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "id_property",
                                            "physicalType": "bigint"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "id_customer",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "id_customer",
                                            "physicalType": "bigint"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "id_broker",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "id_broker",
                                            "physicalType": "bigint"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "sale_date",
                                            "type": "Date",
                                            "physicalType": "date"
                                        },
                                        "sink": {
                                            "name": "sale_date",
                                            "physicalType": "date"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "sale_price",
                                            "type": "Decimal",
                                            "physicalType": "decimal"
                                        },
                                        "sink": {
                                            "name": "sale_price",
                                            "physicalType": "decimal",
                                            "scale": 4,
                                            "precision": 19
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "month_date_sale",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "month_date_sale",
                                            "physicalType": "bigint"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "year_date_sale",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "year_date_sale",
                                            "physicalType": "bigint"
                                        }
                                    }
                                ],
                                "typeConversion": true,
                                "typeConversionSettings": {
                                    "allowDataTruncation": true,
                                    "treatBooleanAsNumber": false
                                }
                            }
                        }
                    }
                ]
            },
            "cw_items_e6s": {
                "type": "Array",
                "defaultValue": [
                    {
                        "source": {
                            "table": "dim_brokers"
                        },
                        "destination": {
                            "schema": "sale",
                            "table": "dim_brokers"
                        },
                        "copySink": {
                            "tableOption": null
                        },
                        "copyActivity": {
                            "translator": {
                                "type": "TabularTranslator",
                                "mappings": [
                                    {
                                        "source": {
                                            "name": "id_broker",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "id_broker",
                                            "physicalType": "bigint"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "name",
                                            "type": "String",
                                            "physicalType": "string"
                                        },
                                        "sink": {
                                            "name": "name",
                                            "physicalType": "varchar",
                                            "length": "8000"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "region",
                                            "type": "String",
                                            "physicalType": "string"
                                        },
                                        "sink": {
                                            "name": "region",
                                            "physicalType": "varchar",
                                            "length": "8000"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "email",
                                            "type": "String",
                                            "physicalType": "string"
                                        },
                                        "sink": {
                                            "name": "email",
                                            "physicalType": "varchar",
                                            "length": "8000"
                                        }
                                    }
                                ]
                            }
                        }
                    },
                    {
                        "source": {
                            "table": "dim_campaigns"
                        },
                        "destination": {
                            "schema": "marketing",
                            "table": "dim_campaigns"
                        },
                        "copySink": {
                            "tableOption": null
                        },
                        "copyActivity": {
                            "translator": {
                                "type": "TabularTranslator",
                                "mappings": [
                                    {
                                        "source": {
                                            "name": "id_campaing",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "id_campaing",
                                            "physicalType": "bigint"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "chanel",
                                            "type": "String",
                                            "physicalType": "string"
                                        },
                                        "sink": {
                                            "name": "chanel",
                                            "physicalType": "varchar",
                                            "length": "8000"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "name",
                                            "type": "String",
                                            "physicalType": "string"
                                        },
                                        "sink": {
                                            "name": "name",
                                            "physicalType": "varchar",
                                            "length": "8000"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "init_date",
                                            "type": "Date",
                                            "physicalType": "date"
                                        },
                                        "sink": {
                                            "name": "init_date",
                                            "physicalType": "date"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "end_date",
                                            "type": "Date",
                                            "physicalType": "date"
                                        },
                                        "sink": {
                                            "name": "end_date",
                                            "physicalType": "date"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "budget",
                                            "type": "Decimal",
                                            "physicalType": "decimal"
                                        },
                                        "sink": {
                                            "name": "budget",
                                            "physicalType": "decimal",
                                            "scale": 4,
                                            "precision": 19
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "Rank_budget",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "Rank_budget",
                                            "physicalType": "bigint"
                                        }
                                    }
                                ]
                            }
                        }
                    },
                    {
                        "source": {
                            "table": "dim_customers"
                        },
                        "destination": {
                            "schema": "sale",
                            "table": "dim_customers"
                        },
                        "copySink": {
                            "tableOption": null
                        },
                        "copyActivity": {
                            "translator": {
                                "type": "TabularTranslator",
                                "mappings": [
                                    {
                                        "source": {
                                            "name": "id_customer",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "id_customer",
                                            "physicalType": "bigint"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "name",
                                            "type": "String",
                                            "physicalType": "string"
                                        },
                                        "sink": {
                                            "name": "name",
                                            "physicalType": "varchar",
                                            "length": "8000"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "last_name",
                                            "type": "String",
                                            "physicalType": "string"
                                        },
                                        "sink": {
                                            "name": "last_name",
                                            "physicalType": "varchar",
                                            "length": "8000"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "email",
                                            "type": "String",
                                            "physicalType": "string"
                                        },
                                        "sink": {
                                            "name": "email",
                                            "physicalType": "varchar",
                                            "length": "8000"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "region",
                                            "type": "String",
                                            "physicalType": "string"
                                        },
                                        "sink": {
                                            "name": "region",
                                            "physicalType": "varchar",
                                            "length": "8000"
                                        }
                                    }
                                ]
                            }
                        }
                    },
                    {
                        "source": {
                            "table": "dim_properties"
                        },
                        "destination": {
                            "schema": "projects",
                            "table": "dim_properties"
                        },
                        "copySink": {
                            "tableOption": "autoCreate"
                        },
                        "copyActivity": {
                            "translator": {
                                "type": "TabularTranslator",
                                "mappings": [
                                    {
                                        "source": {
                                            "name": "id_property",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "id_property",
                                            "physicalType": "bigint"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "id_project",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "id_project",
                                            "physicalType": "bigint"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "type",
                                            "type": "String",
                                            "physicalType": "string"
                                        },
                                        "sink": {
                                            "name": "type",
                                            "physicalType": "varchar",
                                            "length": "8000"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "size_m2",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "size_m2",
                                            "physicalType": "bigint"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "rooms",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "rooms",
                                            "physicalType": "bigint"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "bathroms",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "bathroms",
                                            "physicalType": "bigint"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "price_property",
                                            "type": "Decimal",
                                            "physicalType": "decimal"
                                        },
                                        "sink": {
                                            "name": "price_property",
                                            "physicalType": "decimal",
                                            "scale": 4,
                                            "precision": 19
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "status_property",
                                            "type": "String",
                                            "physicalType": "string"
                                        },
                                        "sink": {
                                            "name": "status_property",
                                            "physicalType": "varchar",
                                            "length": "8000"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "name_project",
                                            "type": "String",
                                            "physicalType": "string"
                                        },
                                        "sink": {
                                            "name": "name_project",
                                            "physicalType": "varchar",
                                            "length": "8000"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "city_project",
                                            "type": "String",
                                            "physicalType": "string"
                                        },
                                        "sink": {
                                            "name": "city_project",
                                            "physicalType": "varchar",
                                            "length": "8000"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "region_project",
                                            "type": "String",
                                            "physicalType": "string"
                                        },
                                        "sink": {
                                            "name": "region_project",
                                            "physicalType": "varchar",
                                            "length": "8000"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "release_date_project",
                                            "type": "Date",
                                            "physicalType": "date"
                                        },
                                        "sink": {
                                            "name": "release_date_project",
                                            "physicalType": "date"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "status_project",
                                            "type": "String",
                                            "physicalType": "string"
                                        },
                                        "sink": {
                                            "name": "status_project",
                                            "physicalType": "varchar",
                                            "length": "8000"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "year_realese_date_project",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "year_realese_date_project",
                                            "physicalType": "bigint"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "Rank_size_m2",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "Rank_size_m2",
                                            "physicalType": "bigint"
                                        }
                                    }
                                ]
                            }
                        }
                    },
                    {
                        "source": {
                            "table": "fac_leads"
                        },
                        "destination": {
                            "schema": "marketing",
                            "table": "fac_leads"
                        },
                        "copySink": {
                            "tableOption": null
                        },
                        "copyActivity": {
                            "translator": {
                                "type": "TabularTranslator",
                                "mappings": [
                                    {
                                        "source": {
                                            "name": "id_lead",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "id_lead",
                                            "physicalType": "bigint"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "id_customer",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "id_customer",
                                            "physicalType": "bigint"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "id_property",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "id_property",
                                            "physicalType": "bigint"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "id_campaing",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "id_campaing",
                                            "physicalType": "bigint"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "init_date",
                                            "type": "Date",
                                            "physicalType": "date"
                                        },
                                        "sink": {
                                            "name": "init_date",
                                            "physicalType": "date"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "chanel",
                                            "type": "String",
                                            "physicalType": "string"
                                        },
                                        "sink": {
                                            "name": "chanel",
                                            "physicalType": "varchar",
                                            "length": "8000"
                                        }
                                    }
                                ]
                            }
                        }
                    },
                    {
                        "source": {
                            "table": "fac_sales"
                        },
                        "destination": {
                            "schema": "sales",
                            "table": "fac_sales"
                        },
                        "copySink": {
                            "tableOption": null
                        },
                        "copyActivity": {
                            "translator": {
                                "type": "TabularTranslator",
                                "mappings": [
                                    {
                                        "source": {
                                            "name": "id_sale",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "id_sale",
                                            "physicalType": "bigint"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "id_property",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "id_property",
                                            "physicalType": "bigint"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "id_customer",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "id_customer",
                                            "physicalType": "bigint"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "id_broker",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "id_broker",
                                            "physicalType": "bigint"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "sale_date",
                                            "type": "Date",
                                            "physicalType": "date"
                                        },
                                        "sink": {
                                            "name": "sale_date",
                                            "physicalType": "date"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "sale_price",
                                            "type": "Decimal",
                                            "physicalType": "decimal"
                                        },
                                        "sink": {
                                            "name": "sale_price",
                                            "physicalType": "decimal",
                                            "scale": 4,
                                            "precision": 19
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "month_date_sale",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "month_date_sale",
                                            "physicalType": "bigint"
                                        }
                                    },
                                    {
                                        "source": {
                                            "name": "year_date_sale",
                                            "type": "Int64",
                                            "physicalType": "long"
                                        },
                                        "sink": {
                                            "name": "year_date_sale",
                                            "physicalType": "bigint"
                                        }
                                    }
                                ]
                            }
                        }
                    }
                ]
            }
        },
        "lastModifiedByObjectId": "64e635ff-9ef2-4d6b-a12a-4f9d3df5a18c",
        "lastPublishTime": "2025-07-06T20:48:09Z"
    }
}