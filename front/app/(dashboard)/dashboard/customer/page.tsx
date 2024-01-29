"use client"
import BreadCrumb from "@/components/breadcrumb";
import { columns } from "@/components/tables/employee-tables/columns";
import { CustomerTable } from "@/components/tables/employee-tables/employee-table";
import { buttonVariants } from "@/components/ui/button";
import { Heading } from "@/components/ui/heading";
import { Separator } from "@/components/ui/separator";
import { Customer, Employee } from "@/constants/data";
import { cn } from "@/lib/utils";
import { Plus } from "lucide-react";
import Link from "next/link";

const breadcrumbItems = [{ title: "customer", link: "/dashboard/customer" }];

type paramsProps = {
  searchParams: {
    [key: string]: string | string[] | undefined;
  };
};

export default async function page({ searchParams }: paramsProps) {
  const page = Number(searchParams.page) || 1;
  const pageLimit = Number(searchParams.limit) || 10;
  const country = searchParams.search || null;
  const offset = (page - 1) * pageLimit;

  const res = await fetch(
    // `https://api.slingacademy.com/v1/sample-data/users?offset=${offset}&limit=${pageLimit}` +
      // (country ? `&search=${country}` : ""),
        `http://localhost:8088/api/customer/${offset}/${pageLimit}`,
        //  +(country ? `&search=${country}` : ""),
  );
  const customerRes = await res.json();
  customerRes.total_users=100;
  const totalUsers = customerRes.total_users; //1000
  const pageCount = Math.ceil(totalUsers / pageLimit);
  const customers: Customer[] = customerRes;
  return (
    <>
      <div className="flex-1 space-y-4  p-4 md:p-8 pt-6">
        <BreadCrumb items={breadcrumbItems} />

        <div className="flex items-start justify-between">
          <Heading
            title={`Employee (${totalUsers})`}
            description="Manage employees (Server side table functionalities.)"
          />

          <Link
            href={"/dashboard/employee/new"}
            className={cn(buttonVariants({ variant: "default" }))}
          >
            <Plus className="mr-2 h-4 w-4" /> Add New
          </Link>
        </div>
        <Separator />

        <CustomerTable
          searchKey="customer_id"
          pageNo={page}
          columns={columns}
          totalUsers={totalUsers}
          data={customers}
          pageCount={pageCount}
        />
      </div>
    </>
  );
}
