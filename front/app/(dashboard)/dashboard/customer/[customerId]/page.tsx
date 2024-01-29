

import BreadCrumb from "@/components/breadcrumb";
import { ProductForm } from "@/components/forms/product-form";
import { usePathname } from 'next/navigation'
import React from "react";

type paramsProps = {
  searchParams: {
    [key: string]: string | string[] | undefined;
  };
};
export default async function Page({searchParams}: paramsProps) {

  const res = await fetch(
    // `https://api.slingacademy.com/v1/sample-data/users?offset=${offset}&limit=${pageLimit}` +
      // (country ? `&search=${country}` : ""),
        `http://localhost:8088/api/customer/1`,
        //  +(country ? `&search=${country}` : ""),
  );
  // const url = window.location.href;

  console.log("url is:"+usePathname.length);
  const customerRes = await res.json();

  const breadcrumbItems = [
    { title: "Employee", link: "/dashboard/customer" },
    { title: "Create", link: "/dashboard/employee/create" },
  ];
  return (
    <div className="flex-1 space-y-4 p-8">
      <BreadCrumb items={breadcrumbItems} />
      <ProductForm
        categories={[
          { _id: "shirts", name: "shirts" },
          { _id: "pants", name: "pants" },
        ]}
        initialData={null}
        key={null}
      />
    </div>
  );
}
