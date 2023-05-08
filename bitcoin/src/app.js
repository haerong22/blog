const { createWallet } = require("./wallet");

const start = async () => {
  const wallet = createWallet();
  console.log(wallet);
};

start();
