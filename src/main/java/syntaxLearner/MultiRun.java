import java.util.Iterator;
import java.util.Map;
import syntaxLearner.UI.Console;
import syntaxLearner.corpus.Vocabulary;
import syntaxLearner.corpus.Word;

/**
 *
 * @author sidkan
 */
public class MultiRun implements Runnable{

    
    int NUMBER_OF_CLUSTERS;
    Iterator<Integer> iter;
    Iterator<Integer> backupIterator;
    Vocabulary vocab;
    Map<Short, Cluster> clusters;
    Learner le;

    MultiRun(int NUMBER_OF_CLUSTERS,Iterator<Integer> iter, Iterator<Integer> backupIterator, Vocabulary vocab, Map<Short, Cluster> clusters, Learner le) {
        this.NUMBER_OF_CLUSTERS = NUMBER_OF_CLUSTERS;
        this.iter = iter;
        this.backupIterator = backupIterator;
        this.vocab = vocab;
        this.clusters = clusters;
        this.le = le;
    
    }
    
    
    
    @Override
    public void run() {
        for (int i=0;i<NUMBER_OF_CLUSTERS;i++){
			//advance in unison, so backupIterator will remember the next word in the hierarchy
			int l = 	iter.next();
			backupIterator.next();
			//make sure you're not clustering a start / end symbol
			Word w = vocab.getWord(l);
			if ((!(w.equals(vocab.START_SYMBOL)))
					&& (!(w.equals(vocab.END_SYMBOL)))) {
				Cluster c = new Cluster(vocab, le );
				c.add(l);
				Console.line(String.format("Created cluster #%1$2s with stem: \"%2$-1s\"",
						c.ID,w.name));
				clusters.put(c.getID(), c);	
			} else {
				//otherwise, skip a step without changing the placemarker
				i--;
				continue;
			}
		}
    }
    
    
    
}
