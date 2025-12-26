package symphony

typealias SheetColumn = String
typealias SheetRow = String

typealias SheetCells = Map<SheetRow, Map<SheetColumn, SheetCell>>
typealias CellGetter = (row: SheetRow) -> Double