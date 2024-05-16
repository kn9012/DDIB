import OrderForm from "@/app/_components/OrderForm";
import styles from "./order.module.scss";

export default function Order() {
  return <OrderForm type="order" orderId="0" orderDate="0" paymentMethod="no" />;
}
