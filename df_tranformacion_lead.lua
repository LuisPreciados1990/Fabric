let
  Source = Lakehouse.Contents(null),
  Navigation = Source{[workspaceId = "9b7090d2-dc30-41fd-8589-dd10f9074a97"]}[Data],
  #"Navigation 1" = Navigation{[lakehouseId = "43d4aaa6-bbf0-43aa-8990-5431b7d5196c"]}[Data],
  #"Navigation 2" = #"Navigation 1"{[Id = "Files", ItemKind = "Folder"]}[Data],
  #"Navigation 3" = #"Navigation 2"{[Name = "silver"]}[Content],
  #"Navigation 4" = #"Navigation 3"{[Name = "leads.csv"]}[Content],
  #"Imported CSV" = Csv.Document(#"Navigation 4", [Delimiter = ",", Columns = 6, QuoteStyle = QuoteStyle.None]),
  #"Promoted headers" = Table.PromoteHeaders(#"Imported CSV", [PromoteAllScalars = true]),
  #"Changed column type" = Table.TransformColumnTypes(#"Promoted headers", {{"id_lead", Int64.Type}, {"id_customer", Int64.Type}, {"id_property", Int64.Type}, {"id_campaing", Int64.Type}, {"init_date", type date}, {"chanel", type text}})
in
  #"Changed column type"