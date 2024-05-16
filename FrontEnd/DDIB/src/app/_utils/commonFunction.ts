import { faker } from "@faker-js/faker";

export function getImage() {
  return faker.image.urlLoremFlickr();
}
