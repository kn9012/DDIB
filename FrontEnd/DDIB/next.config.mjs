/** @type {import('next').NextConfig} */
const nextConfig = {
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
