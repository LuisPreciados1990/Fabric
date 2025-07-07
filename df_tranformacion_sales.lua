let
  Source = Lakehouse.Contents(null),
  Navigation = Source{[workspaceId = "9b7090d2-dc30-41fd-8589-dd10f9074a97"]}[Data],
  #"Navigation 1" = Navigation{[lakehouseId = "43d4aaa6-bbf0-43aa-8990-5431b7d5196c"]}[Data],
  #"Navigation 2" = #"Navigation 1"{[Id = "Files", ItemKind = "Folder"]}[Data],
  #"Navigation 3" = #"Navigation 2"{[Name = "silver"]}[Content],
  #"Navigation 4" = #"Navigation 3"{[Name = "sales.csv"]}[Content],
  #"Imported CSV" = Csv.Document(#"Navigation 4", [Delimiter = ",", Columns = 6, QuoteStyle = QuoteStyle.None]),
  #"Promoted headers" = Table.PromoteHeaders(#"Imported CSV", [PromoteAllScalars = true]),
  #"Changed column type" = Table.TransformColumnTypes(#"Promoted headers", {{"id_sale", Int64.Type}, {"id_property", Int64.Type}, {"id_customer", Int64.Type}, {"id_broker", Int64.Type}, {"sale_date", type date}, {"sale_price", Int64.Type}}),
  #"add month sale" = Table.TransformColumnTypes(Table.AddColumn(#"Changed column type", "month_date_sale", each Date.Month([sale_date])), {{"month_date_sale", Int64.Type}}),
  #"add year sale" = Table.TransformColumnTypes(Table.AddColumn(#"add month sale", "year_date_sale", each Date.Year([sale_date])), {{"year_date_sale", Int64.Type}}),
  #"Changed column type 1" = Table.TransformColumnTypes(#"add year sale", {{"sale_price", Currency.Type}})
in
  #"Changed column type 1"