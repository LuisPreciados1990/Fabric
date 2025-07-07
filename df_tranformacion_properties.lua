let
  Source = Lakehouse.Contents(null),
  Navigation = Source{[workspaceId = "9b7090d2-dc30-41fd-8589-dd10f9074a97"]}[Data],
  #"Navigation 1" = Navigation{[lakehouseId = "43d4aaa6-bbf0-43aa-8990-5431b7d5196c"]}[Data],
  #"Navigation 2" = #"Navigation 1"{[Id = "Files", ItemKind = "Folder"]}[Data],
  #"Navigation 3" = #"Navigation 2"{[Name = "silver"]}[Content],
  #"Navigation 4" = #"Navigation 3"{[Name = "properties.csv"]}[Content],
  #"Imported CSV" = Csv.Document(#"Navigation 4", [Delimiter = ",", Columns = 13, Encoding = 65001, QuoteStyle = QuoteStyle.None]),
  #"Promoted headers" = Table.PromoteHeaders(#"Imported CSV", [PromoteAllScalars = true]),
  #"Changed column type" = Table.TransformColumnTypes(#"Promoted headers", {{"id_property", Int64.Type}, {"id_project", Int64.Type}, {"type", type text}, {"size_m2", Int64.Type}, {"rooms", Int64.Type}, {"bathroms", Int64.Type}, {"price_property", Currency.Type}, {"status_property", type text}, {"name_project", type text}, {"city_project", type text}, {"region_project", type text}, {"release_date_project", type date}, {"status_project", type text}}),
  #"Added custom" = Table.TransformColumnTypes(Table.AddColumn(#"Changed column type", "year_realese_date_project", each Date.Year([release_date_project])), {{"year_realese_date_project", Int64.Type}}),
  #"Added rank column" = Table.AddRankColumn(#"Added custom", "Rank", {{"size_m2", Order.Descending}}),
  #"Renamed columns" = Table.RenameColumns(#"Added rank column", {{"Rank", "Rank_size_m2"}})
in
  #"Renamed columns"