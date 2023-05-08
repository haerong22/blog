const bitcore = require("bitcore-lib");
const { testnet } = require("bitcore-lib/lib/networks");

const createWallet = () => {
  const privateKey = new bitcore.PrivateKey();
  const address = privateKey.toAddress();
  return {
    privateKey: privateKey.toString(),
    address: address.toString(),
  };
};

module.exports = {
  createWallet: createWallet,
};
