const axios = require("axios");
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

const validWalletAddress = async (address) => {
  return bitcore.Address.isValid(address, testnet);
};

const getBalance = async (address) => {
  const isValid = await validWalletAddress(address);

  if (!isValid) {
    return "invalid address";
  }

  const url = `https://api.blockcypher.com/v1/btc/test3/addrs/${address}/balance`;
  const balance = await axios.get(url);

  return balance.data.balance;
};

module.exports = {
  createWallet: createWallet,
  getBalance: getBalance,
};
