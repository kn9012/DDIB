import { faker } from "@faker-js/faker";

export function getImage() {
  return faker.image.urlLoremFlickr();
}

export function getDiscount(price: number, discount: number) {
  const sale = price * (discount * 0.01);
  return price - sale;
}
