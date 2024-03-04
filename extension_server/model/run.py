import sys
from transformers import pipeline


if __name__ == "__main__":
    inp = sys.argv[1]
    print(inp)
    summarizer = pipeline("summarization", model="sshleifer/distilbart-cnn-12-6")
    with open(sys.argv[1][:-4] + "_out.txt", "w+") as f:
        f.write(summarizer(inp, max_length=130, min_length=30, do_sample=False)[0]['summary_text'])