const { createWallet, getBalance } = require("./wallet");

const start = async () => {
  //   const wallet = createWallet();
  //   console.log(wallet);

  const balance = await getBalance("mwRoSmns2iWowMmsYAxwDukyTgaDx3qvGj");
  console.log("wallet balance : ", balance);
};

start();
