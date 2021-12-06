/*
 * Copyright 2020 Marco Bignami.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.unknowndomain.alea.systems.yze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.unknowndomain.alea.messages.MsgBuilder;
import net.unknowndomain.alea.random.SingleResult;
import net.unknowndomain.alea.roll.LocalizedResult;

/**
 *
 * @author journeyman
 */
public class YearZeroResults extends LocalizedResult
{
    private final static String BUNDLE_NAME = "net.unknowndomain.alea.systems.yze.RpgSystemBundle";
    
    private final List<SingleResult<Integer>> baseResults;
    private final List<SingleResult<Integer>> gearResults;
    private final List<SingleResult<Integer>> skillResults;
    private final List<SingleResult<Integer>> stressResults;
    private SingleResult<Integer> panicResult;
    private int successes = 0;
    private int baseBanes = 0;
    private int gearBanes = 0;
    private int stressBanes = 0;
    private boolean panic = false;
    private YearZeroResults prev;
    
    public YearZeroResults(List<SingleResult<Integer>> baseResults, List<SingleResult<Integer>> gearResults, List<SingleResult<Integer>> skillResults, List<SingleResult<Integer>> stressResults)
    {
        List<SingleResult<Integer>> tmp = new ArrayList<>(baseResults.size());
        tmp.addAll(baseResults);
        this.baseResults = Collections.unmodifiableList(tmp);
        tmp = new ArrayList<>(gearResults.size());
        tmp.addAll(gearResults);
        this.gearResults = Collections.unmodifiableList(tmp);
        tmp = new ArrayList<>(skillResults.size());
        tmp.addAll(skillResults);
        this.skillResults = Collections.unmodifiableList(tmp);
        tmp = new ArrayList<>(stressResults.size());
        tmp.addAll(stressResults);
        this.stressResults = Collections.unmodifiableList(tmp);
    }
    
    public void addSuccess()
    {
        successes++;
    }
    
    public void addBaseBane()
    {
        baseBanes ++;
    }
    
    public void addGearBane()
    {
        gearBanes ++;
    }
    
    public void addStressBane()
    {
        stressBanes ++;
    }

    public int getSuccesses()
    {
        return successes;
    }
    
    public YearZeroResults getPrev()
    {
        return prev;
    }

    public void setPrev(YearZeroResults prev)
    {
        this.prev = prev;
    }
    
    @Override
    protected void formatResults(MsgBuilder messageBuilder, boolean verbose, int indentValue)
    {
        String indent = getIndent(indentValue);
        messageBuilder.append(indent).append(translate("yze.results.successes",getSuccesses())).appendNewLine();
        if (getBanes() > 0)
        {
            messageBuilder.append(indent).append(translate("yze.results.banes",getBanes()));
            messageBuilder.append("( ").append((translate("yze.results.base",getBaseBanes())));
            messageBuilder.append(" ").append(translate("yze.results.gear",getGearBanes()));
            if (!stressResults.isEmpty())
            {
                messageBuilder.append(" ").append(translate("yze.results.stress",getStressBanes()));
            }
            messageBuilder.append(" )").appendNewLine();
        }
        if (panic)
        {
            messageBuilder.append(indent).append(translate("yze.results.panicRoll"));
            messageBuilder.append("( ").append(panicResult.getLabel()).append(" => ");
            messageBuilder.append(panicResult.getValue()).append(" )").appendNewLine();
        }
        if (verbose)
        {
            messageBuilder.append(indent).append("Roll ID: ").append(getUuid()).appendNewLine();
            messageBuilder.append(indent).append(translate("yze.results.baseResults")).append(" [ ");
            for (SingleResult<Integer> t : getBaseResults())
            {
                messageBuilder.append("( ").append(t.getLabel()).append(" => ");
                messageBuilder.append(t.getValue()).append(") ");
            }
            messageBuilder.append("]\n");
            messageBuilder.append(indent).append(translate("yze.results.gearResults")).append(" [ ");
            for (SingleResult<Integer> t : getGearResults())
            {
                messageBuilder.append("( ").append(t.getLabel()).append(" => ");
                messageBuilder.append(t.getValue()).append(") ");
            }
            messageBuilder.append("]\n");
            messageBuilder.append(indent).append(translate("yze.results.skillResults")).append(" [ ");
            for (SingleResult<Integer> t : getSkillResults())
            {
                messageBuilder.append("( ").append(t.getLabel()).append(" => ");
                messageBuilder.append(t.getValue()).append(") ");
            }
            messageBuilder.append("]\n");
            if (!stressResults.isEmpty())
            {
                messageBuilder.append(indent).append(translate("yze.results.stressResults")).append(" [ ");
                for (SingleResult<Integer> t : getStressResults())
                {
                    messageBuilder.append("( ").append(t.getLabel()).append(" => ");
                    messageBuilder.append(t.getValue()).append(") ");
                }
                messageBuilder.append("]\n");
            }
            if (prev != null)
            {
                messageBuilder.append(translate("yze.results.prevResults")).append("{\n");
                prev.formatResults(messageBuilder, verbose, indentValue + 4);
                messageBuilder.append("}\n");
            }
        }
    }

    public int getGearBanes()
    {
        return gearBanes;
    }

    public List<SingleResult<Integer>> getBaseResults()
    {
        return baseResults;
    }

    public List<SingleResult<Integer>> getGearResults()
    {
        return gearResults;
    }

    public List<SingleResult<Integer>> getSkillResults()
    {
        return skillResults;
    }

    public List<SingleResult<Integer>> getStressResults()
    {
        return stressResults;
    }

    public boolean isPanic()
    {
        return panic;
    }

    public void setPanic(boolean panic)
    {
        this.panic = panic;
    }

    public SingleResult<Integer> getPanicResult()
    {
        return panicResult;
    }

    public void setPanicResult(SingleResult<Integer> panicResult)
    {
        this.panicResult = panicResult;
    }

    public int getBaseBanes()
    {
        return baseBanes;
    }

    public int getStressBanes()
    {
        return stressBanes;
    }

    public int getBanes()
    {
        return baseBanes + gearBanes + stressBanes;
    }

    @Override
    protected String getBundleName()
    {
        return BUNDLE_NAME;
    }

}
