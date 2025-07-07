{
    "name": "PIPE_DATAPATH_ORCHESTRATOR_PY_LHP_01",
    "objectId": "8a73c362-c03a-4d60-937f-271192fb1a65",
    "properties": {
        "activities": [
            {
                "name": "CP_DATAPATH_EXTRACT_PY_LHP_001",
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
                        "type": "DelimitedTextSource",
                        "storeSettings": {
                            "type": "AzureBlobFSReadSettings",
                            "recursive": true,
                            "wildcardFolderPath": "raw_data",
                            "wildcardFileName": "*",
                            "enablePartitionDiscovery": false
                        },
                        "formatSettings": {
                            "type": "DelimitedTextReadSettings"
                        },
                        "datasetSettings": {
                            "annotations": [],
                            "type": "DelimitedText",
                            "typeProperties": {
                                "location": {
                                    "type": "AzureBlobFSLocation",
                                    "fileSystem": "realstate"
                                },
                                "columnDelimiter": ",",
                                "escapeChar": "\\",
                                "firstRowAsHeader": true,
                                "quoteChar": "\""
                            },
                            "schema": [],
                            "externalReferences": {
                                "connection": "039e8e89-3f56-403c-b380-4e2cbef17e34"
                            }
                        }
                    },
                    "sink": {
                        "type": "DelimitedTextSink",
                        "storeSettings": {
                            "type": "LakehouseWriteSettings"
                        },
                        "formatSettings": {
                            "type": "DelimitedTextWriteSettings",
                            "fileExtension": ".txt"
                        },
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
                                        "rootFolder": "Files"
                                    }
                                }
                            },
                            "type": "DelimitedText",
                            "typeProperties": {
                                "location": {
                                    "type": "LakehouseLocation",
                                    "folderPath": "broze"
                                },
                                "columnDelimiter": ",",
                                "escapeChar": "\\",
                                "firstRowAsHeader": true,
                                "quoteChar": "\""
                            },
                            "schema": []
                        }
                    },
                    "enableStaging": false,
                    "translator": {
                        "type": "TabularTranslator",
                        "typeConversion": true,
                        "typeConversionSettings": {
                            "allowDataTruncation": true,
                            "treatBooleanAsNumber": false
                        }
                    }
                }
            },
            {
                "name": "NTB_DATAPATH_STRUCTURE_PY_LHP_01",
                "type": "TridentNotebook",
                "dependsOn": [
                    {
                        "activity": "CP_DATAPATH_EXTRACT_PY_LHP_001",
                        "dependencyConditions": [
                            "Succeeded"
                        ]
                    }
                ],
                "policy": {
                    "timeout": "0.12:00:00",
                    "retry": 0,
                    "retryIntervalInSeconds": 30,
                    "secureOutput": false,
                    "secureInput": false
                },
                "typeProperties": {
                    "notebookId": "160391b3-c478-47fb-b025-f877fb5ad3c1",
                    "workspaceId": "9b7090d2-dc30-41fd-8589-dd10f9074a97"
                }
            },
            {
                "name": "DT_DATAPATH_TRANSFORMER_PY_LHP_01",
                "type": "RefreshDataflow",
                "dependsOn": [
                    {
                        "activity": "NTB_DATAPATH_STRUCTURE_PY_LHP_01",
                        "dependencyConditions": [
                            "Succeeded"
                        ]
                    }
                ],
                "policy": {
                    "timeout": "0.12:00:00",
                    "retry": 0,
                    "retryIntervalInSeconds": 30,
                    "secureOutput": false,
                    "secureInput": false
                },
                "typeProperties": {
                    "dataflowId": "ee335702-09bd-4472-8818-c9282bb55516",
                    "workspaceId": "9b7090d2-dc30-41fd-8589-dd10f9074a97",
                    "notifyOption": "NoNotification",
                    "dataflowType": "Dataflow-Gen2"
                }
            },
            {
                "name": "NB_DATAPATH_DISTRIBUTION_PY_LHP_01",
                "type": "TridentNotebook",
                "dependsOn": [
                    {
                        "activity": "DT_DATAPATH_TRANSFORMER_PY_LHP_01",
                        "dependencyConditions": [
                            "Succeeded"
                        ]
                    }
                ],
                "policy": {
                    "timeout": "0.12:00:00",
                    "retry": 0,
                    "retryIntervalInSeconds": 30,
                    "secureOutput": false,
                    "secureInput": false
                },
                "typeProperties": {
                    "notebookId": "71b9898f-1d30-4a2b-833e-de9b81bff998",
                    "workspaceId": "9b7090d2-dc30-41fd-8589-dd10f9074a97"
                }
            },
            {
                "name": "import_data_table_wharehouse",
                "type": "InvokePipeline",
                "state": "Inactive",
                "onInactiveMarkAs": "Succeeded",
                "dependsOn": [
                    {
                        "activity": "NB_DATAPATH_DISTRIBUTION_PY_LHP_01",
                        "dependencyConditions": [
                            "Succeeded"
                        ]
                    }
                ],
                "policy": {
                    "timeout": "0.12:00:00",
                    "retry": 0,
                    "retryIntervalInSeconds": 30,
                    "secureOutput": false,
                    "secureInput": false
                },
                "typeProperties": {
                    "waitOnCompletion": true,
                    "operationType": "InvokeFabricPipeline",
                    "pipelineId": "8ebc5b3d-1d6e-4654-8844-f7c282185e94",
                    "workspaceId": "9b7090d2-dc30-41fd-8589-dd10f9074a97",
                    "parameters": {
                        "cw_items_ql4": [
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
                        ],
                        "cw_items_e6s": [
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
                }
            }
        ],
        "lastModifiedByObjectId": "64e635ff-9ef2-4d6b-a12a-4f9d3df5a18c",
        "lastPublishTime": "2025-07-07T19:13:13Z"
    }
}