const API_URL = "http://localhost:8080/api/V1/player";


export const getPlayers = async () => {

    const timeout = 5000;
    const controller = new AbortController;
    const timeoutId = setTimeout(() => controller.abort(), timeout);

    try {

       const response = await fetch(API_URL, {

            signal: controller.signal,
        });

        clearTimeout(timeoutId);

        if (!response.ok) {

            throw new Error("Failed to fetch players");
        }

        const data = await response.json();
        return data;
    } catch (error) {

        console.error("Error fetching players: ", error);
        return [];
    }
};