"use client";
import { Checkbox } from "@/components/ui/checkbox";
import { Customer, Employee } from "@/constants/data";
import { ColumnDef } from "@tanstack/react-table";
import { CellAction } from "./cell-action";

export const columns: ColumnDef<Customer>[] = [
  {
    id: "select",
    header: ({ table }) => (
      <Checkbox
        checked={table.getIsAllPageRowsSelected()}
        onCheckedChange={(value) => table.toggleAllPageRowsSelected(!!value)}
        aria-label="Select all"
      />
    ),
    cell: ({ row }) => (
      <Checkbox
        checked={row.getIsSelected()}
        onCheckedChange={(value) => row.toggleSelected(!!value)}
        aria-label="Select row"
      />
    ),
    enableSorting: false,
    enableHiding: false,
  },
  {
    accessorKey: "customer_id",
    header: "id",
  },

  {
    accessorKey: "firstName",
    header: "firstNAME",
  },
  {
    accessorKey: "lastName",
    header: "lst_name",
  },
  {
    accessorKey: "email",
    header: "EMAIL",
  },
  // {
  //   accessorKey: "job",
  //   header: "COMPANY",
  // },
  // {
  //   accessorKey: "gender",
  //   header: "GENDER",
  // },
  {
    id: "actions",
    cell: ({ row }) => <CellAction data={row.original} />,
  },
];
