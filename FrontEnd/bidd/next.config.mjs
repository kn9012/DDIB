/** @type {import('next').NextConfig} */
const nextConfig = {
  webpack5: true,
  webpack: (config, options) => { config.cache = false; return config; },
  images: {
    remotePatterns: [
      {
        protocol: "https",
        hostname: "iandwe.s3.ap-northeast-2.amazonaws.com",
        port: "",
        pathname: "/thumbnail/*",
      },
      {
        protocol: "https",
        hostname: "iandwe.s3.ap-northeast-2.amazonaws.com",
        port: "",
        pathname: "/details/*",
      },
    ],
  },
};

export default nextConfig;
