/** @type {import('tailwindcss').Config} */
import { nextui } from "@nextui-org/react";

export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
    "./node_modules/@nextui-org/theme/dist/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {},
  },
  darkMode: "class",
  plugins: [
    nextui({
      themes: {
        dark: {
          colors: {
            primary: {
              50: "#fffbeb",
              100: "#fef3c7",
              200: "#fde68a",
              300: "#fcd34d",
              400: "#fbbf24",
              500: "#FFB703", // main color
              600: "#d97706",
              700: "#b45309",
              800: "#92400e",
              900: "#78350f",
              DEFAULT: "#FFB703",
              foreground: "#000000",
            },
          },
        },
        light: {
          colors: {
            primary: {
              50: "#fffbeb",
              100: "#fef3c7",
              200: "#fde68a",
              300: "#fcd34d",
              400: "#fbbf24",
              500: "#FFB703", // main color
              600: "#d97706",
              700: "#b45309",
              800: "#92400e",
              900: "#78350f",
              DEFAULT: "#FFB703",
              foreground: "#000000",
            },
          },
        },
      },
    }),
  ],
};
