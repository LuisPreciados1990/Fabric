let
  Source = Lakehouse.Contents(null),
  Navigation = Source{[workspaceId = "9b7090d2-dc30-41fd-8589-dd10f9074a97"]}[Data],
  #"Navigation 1" = Navigation{[lakehouseId = "43d4aaa6-bbf0-43aa-8990-5431b7d5196c"]}[Data],
  #"Navigation 2" = #"Navigation 1"{[Id = "Files", ItemKind = "Folder"]}[Data],
  #"Navigation 3" = #"Navigation 2"{[Name = "silver"]}[Content],
  #"Navigation 4" = #"Navigation 3"{[Name = "campaigns.csv"]}[Content],
  #"Imported CSV" = Csv.Document(#"Navigation 4", [Delimiter = ",", Columns = 6, Encoding = 65001, QuoteStyle = QuoteStyle.None]),
  #"Promoted headers" = Table.PromoteHeaders(#"Imported CSV", [PromoteAllScalars = true]),
  #"Changed column type" = Table.TransformColumnTypes(#"Promoted headers", {{"budget", Currency.Type}, {"id_campaing", Int64.Type}, {"chanel", type text}, {"name", type text}, {"init_date", type date}, {"end_date", type date}}),
  #"Added rank column" = Table.AddRankColumn(#"Changed column type", "Rank", {{"budget", Order.Descending}}),
  #"Renamed columns" = Table.RenameColumns(#"Added rank column", {{"Rank", "Rank_budget"}})
in
  #"Renamed columns"